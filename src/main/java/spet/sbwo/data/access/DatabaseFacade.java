package spet.sbwo.data.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spet.sbwo.data.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class DatabaseFacade implements IBackupCreator, IDatabaseExecutorCreator {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseFacade.class);
    private EntityManagerFactory emf;

    public DatabaseFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public IDatabaseExecutor createExecutor() {
        return new DatabaseExecutor(this.emf.createEntityManager());
    }

    @Override
    public void createBackup(String baseName) {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            em.createNativeQuery(String.format("BACKUP TO '%s.zip'", baseName)).executeUpdate();
        } catch (Exception e) {
            LOG.error("Unable to run backup", e);
            throw new DatabaseException(e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            if (em != null) {
                em.close();
            }
        }
    }

}
