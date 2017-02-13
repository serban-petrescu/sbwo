package spet.sbwo.data.access;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseError;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.query.CountFacade;
import spet.sbwo.data.query.IQueryFacade;
import spet.sbwo.data.query.SelectAttributeFacade;
import spet.sbwo.data.query.SelectFacade;
import spet.sbwo.data.query.SelectSingleFacade;

class DatabaseExecutor implements IDatabaseExecutor {
	static final Logger LOG = LoggerFactory.getLogger(DatabaseExecutor.class);

	protected final EntityManager em;
	protected EntityTransaction tr;

	DatabaseExecutor(EntityManager em) throws DatabaseException {
		this.em = em;
		this.tr = null;
		this.start();
	}

	@Override
	public <T> void update(T entity) throws DatabaseException {
		try {
			em.merge(entity);
		} catch (Exception e) {
			LOG.warn("Database error while executing an update.", e);
			throw new DatabaseException(e);
		}
	}

	@Override
	public <T> void delete(T entity) throws DatabaseException {
		try {
			em.remove(entity);
		} catch (Exception e) {
			LOG.warn("Database error while executing a delete.", e);
			throw new DatabaseException(e);
		}
	}

	@Override
	public <T> void create(T entity) throws DatabaseException {
		try {
			em.persist(entity);
		} catch (Exception e) {
			LOG.warn("Database error while executing a create.", e);
			throw new DatabaseException(e);
		}
	}

	@Override
	public void start() throws DatabaseException {
		if (this.tr == null) {
			this.tr = em.getTransaction();
			this.tr.begin();
		} else {
			throw new DatabaseException(DatabaseError.OTHER, "Transaction already started.");
		}
	}

	@Override
	public void commit() throws DatabaseException {
		this.commit(false);
	}

	@Override
	public void commit(boolean start) throws DatabaseException {
		if (this.tr != null) {
			try {
				this.tr.commit();
			} catch (RollbackException e) {
				LOG.warn("Transaction commit has failed.", e);
				throw new DatabaseException(e);
			}
			this.tr = null;
		}
		if (start) {
			this.start();
		}
	}

	@Override
	public void rollback() throws DatabaseException {
		this.rollback(false);
	}

	@Override
	public void rollback(boolean start) throws DatabaseException {
		if (this.tr != null) {
			this.tr.rollback();
		} else {
			throw new DatabaseException(DatabaseError.OTHER, "Attempted to rolback without a transaction.");
		}
		this.tr = null;
		if (start) {
			this.start();
		}
	}

	@Override
	public void close() {
		try {
			if (this.tr != null) {
				this.tr.rollback();
			}
			if (this.em != null) {
				this.em.close();
			}
		} catch (Exception e) {
			LOG.error("Error while closing executor.", e);
		}
	}

	@Override
	public <T> IQueryFacade<Long> count(Class<T> entity) {
		return new CountFacade<>(em, entity);
	}

	@Override
	public <T> IQueryFacade<List<T>> select(Class<T> entity) {
		return new SelectFacade<>(em, entity);
	}

	@Override
	public <T, M> IQueryFacade<List<M>> select(Class<T> entityClazz, Class<M> attrClazz, String attr) {
		return new SelectAttributeFacade<>(em, entityClazz, attrClazz, attr);
	}

	@Override
	public <T> IQueryFacade<T> selectSingle(Class<T> entity) {
		return new SelectSingleFacade<>(em, entity);
	}

	@Override
	public <T> T find(Class<T> clazz, Object id) throws DatabaseException {
		try {
			return em.find(clazz, id);
		} catch (Exception e) {
			LOG.error("Unable to find a entity", e);
			throw new DatabaseException(e);
		}
	}

}
