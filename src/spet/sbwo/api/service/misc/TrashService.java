package spet.sbwo.api.service.misc;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.CollectionType;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.control.controller.misc.TrashController;

@Path("/trash")
public class TrashService extends BaseService {
	private TrashController controller;
	private ObjectReader jsonReader;

	public TrashService(TrashController controller) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, TrashChannel.class);
		this.controller = controller;
		this.jsonReader = mapper.readerFor(collectionType);
	}

	@DELETE
	@Path("/delete")
	@Consumes("application/json")
	public void delete(InputStream body) {
		try {
			List<TrashChannel> data = this.jsonReader.readValue(body);
			this.controller.delete(data, currentUsername());
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@DELETE
	@Path("/delete/all")
	@Consumes("application/json")
	public void deleteAll() {
		try {
			this.controller.deleteAll(currentUsername());
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/restore")
	@Consumes("application/json")
	public void restore(InputStream body) {
		try {
			List<TrashChannel> data = this.jsonReader.readValue(body);
			this.controller.restore(data, currentUsername());
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
