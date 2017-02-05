package spet.sbwo.api.service.misc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.control.controller.misc.CountController;

@Path("/utility")
public class CountService extends BaseService {
	private final CountController controller;
	private final ObjectWriter countWriter;

	public CountService(CountController utilityController) {
		this.controller = utilityController;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.countWriter = mapper.writerFor(CountChannel.class);
	}

	@GET
	@Path("/count")
	@Produces("application/json")
	public String count() {
		try {
			return countWriter.writeValueAsString(controller.readCounts());
		} catch (Exception e) {
			throw mapException(e);
		}
	}

}
