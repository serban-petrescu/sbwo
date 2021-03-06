package spet.sbwo.layer;

import org.picocontainer.MutablePicoContainer;
import spet.sbwo.data.access.DatabaseFacade;

import javax.persistence.Persistence;

public class Database {
    static final String SBWO_PU = "sbwo";

    private Database() {
        super();
    }

    public static void install(MutablePicoContainer container) {
        container.addComponent(Persistence.createEntityManagerFactory(SBWO_PU));
        container.addComponent(DatabaseFacade.class);
    }

}
