package spet.sbwo.api;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.CollectionType;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.control.controller.TrashController;

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
	public void delete(@Context HttpServletRequest request, InputStream body) {
		try {
			List<TrashChannel> data = this.jsonReader.readValue(body);
			this.controller.delete(data, getCurrentUsername(request));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@DELETE
	@Path("/delete/all")
	@Consumes("application/json")
	public void delete(@Context HttpServletRequest request) {
		try {
			this.controller.deleteAll(getCurrentUsername(request));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@PUT
	@Path("/restore")
	@Consumes("application/json")
	public void restore(@Context HttpServletRequest request, InputStream body) {
		try {
			List<TrashChannel> data = this.jsonReader.readValue(body);
			this.controller.restore(data, getCurrentUsername(request));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}
}
