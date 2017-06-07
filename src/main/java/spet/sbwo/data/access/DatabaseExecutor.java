package spet.sbwo.data.access;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseError;
import spet.sbwo.data.DatabaseException;

class DatabaseExecutor implements IDatabaseExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseExecutor.class);

    protected final EntityManager em;
    protected EntityTransaction tr;

    DatabaseExecutor(EntityManager em) {
        this.em = em;
        this.tr = null;
        this.start();
    }

    @Override
    public <T> void update(T entity) {
        try {
            em.merge(entity);
        } catch (Exception e) {
            LOG.warn("Database error while executing an update.");
            throw new DatabaseException(e);
        }
    }

    @Override
    public <T> void delete(T entity) {
        try {
            em.remove(entity);
        } catch (Exception e) {
            LOG.warn("Database error while executing a delete.");
            throw new DatabaseException(e);
        }
    }

    @Override
    public <T> void create(T entity) {
        try {
            em.persist(entity);
        } catch (Exception e) {
            LOG.warn("Database error while executing a create.");
            throw new DatabaseException(e);
        }
    }

    @Override
    public void start() {
        if (this.tr == null) {
            this.tr = em.getTransaction();
            this.tr.begin();
        } else {
            throw new DatabaseException(DatabaseError.OTHER, "Transaction already started.");
        }
    }

    @Override
    public void commit() {
        this.commit(false);
    }

    @Override
    public void commit(boolean start) {
        if (this.tr != null) {
            try {
                this.tr.commit();
            } catch (Exception e) {
                LOG.error("Transaction commit has failed.", e);
                throw new DatabaseException(e);
            } finally {
                this.tr = null;
            }
        }
        if (start) {
            this.start();
        }
    }

    @Override
    public void rollback() {
        this.rollback(false);
    }

    @Override
    public void rollback(boolean start) {
        if (this.tr != null) {
            try {
                this.tr.rollback();
            } catch (Exception e) {
                LOG.warn("Transaction rollback has failed.");
                throw new DatabaseException(e);
            } finally {
                this.tr = null;
            }
        }
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
    public <T> T find(Class<T> clazz, Object id) {
        try {
            return em.find(clazz, id);
        } catch (Exception e) {
            LOG.error("Unable to find a entity ({}, {}).", clazz.getSimpleName(), id);
            throw new DatabaseException(e);
        }
    }

    @Override
    public <T> List<T> queryList(String name, Class<T> resultType, Object... params) {
        try {
            TypedQuery<T> query = em.createNamedQuery(name, resultType);
            for (int i = 0; i < params.length; ++i) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        } catch (Exception e) {
            LOG.error("Unable to run named query {}.", name);
            throw new DatabaseException(e);
        }
    }

    @Override
    public <T> Optional<T> querySingle(String name, Class<T> resultType, Object... params) {
        return queryList(name, resultType, params).stream().findFirst();
    }

    @Override
    public <T> List<T> queryListLimit(String name, Class<T> resultType, int maxResults, Object... params) {
        try {
            TypedQuery<T> query = em.createNamedQuery(name, resultType);
            for (int i = 0; i < params.length; ++i) {
                query.setParameter(i + 1, params[i]);
            }
            query.setMaxResults(maxResults);
            return query.getResultList();
        } catch (Exception e) {
            LOG.error("Unable to run named query {}.", name);
            throw new DatabaseException(e);
        }
    }

}
