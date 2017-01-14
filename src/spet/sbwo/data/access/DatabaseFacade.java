package spet.sbwo.data.access;

import javax.persistence.EntityManagerFactory;

import spet.sbwo.data.DatabaseException;

public class DatabaseFacade {
	private EntityManagerFactory emf;

	public DatabaseFacade(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public IDatabaseExecutor buildExecutor(boolean deferred) throws DatabaseException {
		if (deferred) {
			return new DeferredDatabaseExecutor(this.emf.createEntityManager());
		} else {
			return new ImmediateDatabaseExecutor(this.emf.createEntityManager());
		}
	}
}
