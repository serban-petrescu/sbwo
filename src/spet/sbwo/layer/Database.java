package spet.sbwo.layer;

import javax.persistence.Persistence;

import org.picocontainer.MutablePicoContainer;

import spet.sbwo.data.access.DatabaseFacade;

public class Database {
	static final String SBWO_PU = "sbwo";

	public Database(MutablePicoContainer container) {
		container.addComponent(Persistence.createEntityManagerFactory(SBWO_PU));
		container.addComponent(DatabaseFacade.class);
	}

}
