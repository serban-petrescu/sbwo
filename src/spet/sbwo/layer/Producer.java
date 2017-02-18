package spet.sbwo.layer;

import org.picocontainer.MutablePicoContainer;

import spet.sbwo.api.service.util.JsonProducer;
import spet.sbwo.api.service.util.JsonpProducer;

public class Producer {

	public Producer(MutablePicoContainer container) {
		container.addComponent(JsonpProducer.class);
		container.addComponent(JsonProducer.class);
	}

}
