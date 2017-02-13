package spet.sbwo.layer;

import java.util.Arrays;
import java.util.List;

import spet.sbwo.api.util.JsonProducer;
import spet.sbwo.api.util.JsonpProducer;

public class Producer {
	private final JsonProducer jsonProducer;
	private final JsonpProducer jsonpProducer;

	public Producer() {
		this.jsonpProducer = new JsonpProducer();
		this.jsonProducer = new JsonProducer();
	}

	public List<Object> getProducers() {
		return Arrays.asList(jsonProducer, jsonpProducer);
	}

}
