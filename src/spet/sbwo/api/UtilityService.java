package spet.sbwo.api;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.control.controller.UtilityController;

@Path("/utility")
public class UtilityService extends BaseService {
	private UtilityController controller;
	private ObjectWriter countWriter;

	public UtilityService(UtilityController utilityController) {
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
			return this.countWriter.writeValueAsString(this.controller.readCounts());
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

}
