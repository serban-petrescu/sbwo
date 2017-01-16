package spet.sbwo.data.access;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseException;

class ImmediateDatabaseExecutor extends BaseDatabaseExecutor {
	private static final Logger LOG = LoggerFactory.getLogger(ImmediateDatabaseExecutor.class);

	ImmediateDatabaseExecutor(EntityManager em) throws DatabaseException {
		super(em);
	}

	@Override
	public <T> void update(T entity) throws DatabaseException {
		try {
			simpleCommandBuilder.update(entity).run(this.em);
		} catch (Exception e) {
			LOG.warn("Database error while executing an update.", e);
			throw new DatabaseException(e);
		}
	}

	@Override
	public <T> void delete(T entity) throws DatabaseException {
		try {
			simpleCommandBuilder.delete(entity).run(this.em);
		} catch (Exception e) {
			LOG.warn("Database error while executing a delete.", e);
			throw new DatabaseException(e);
		}
	}

	@Override
	public <T> void create(T entity) throws DatabaseException {
		try {
			simpleCommandBuilder.create(entity).run(this.em);
		} catch (Exception e) {
			LOG.warn("Database error while executing a create.", e);
			throw new DatabaseException(e);
		}
	}
}
