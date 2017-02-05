package spet.sbwo.layer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import spet.sbwo.data.access.DatabaseFacade;

public class Database {
	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sbwo");
	private final DatabaseFacade facade = new DatabaseFacade(emf);

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public DatabaseFacade getFacade() {
		return facade;
	}

}
