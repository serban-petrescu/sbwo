package spet.sbwo.layer;

import org.picocontainer.MutablePicoContainer;

import spet.sbwo.api.service.util.JsonProducer;
import spet.sbwo.api.service.util.JsonpProducer;

public class Producer {

    private Producer() {
        super();
    }

    public static void install(MutablePicoContainer container) {
        container.addComponent(JsonpProducer.class);
        container.addComponent(JsonProducer.class);
    }

}
