package spet.sbwo.api.service.user;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.control.controller.user.TileController;

@Path("/user/tiles")
public class TileService extends BaseService {
	private final TileController controller;
	private final ObjectWriter tileWriter;
	private final ObjectReader tileReader;

	public TileService(TileController controller) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.controller = controller;
		this.tileReader = mapper.readerFor(UserHomeTilesChannel.class);
		this.tileWriter = mapper.writerFor(UserHomeTilesChannel.class);
	}

	@GET
	@Path("/read")
	@Produces("application/json")
	public String readTiles() {
		try {
			UserHomeTilesChannel data = controller.readTiles(currentUsername());
			return this.tileWriter.writeValueAsString(data);
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")
	public String updateTiles(InputStream body) {
		try {
			UserHomeTilesChannel input = this.tileReader.readValue(body);
			UserHomeTilesChannel result = controller.updateTiles(input, currentUsername());
			return this.tileWriter.writeValueAsString(result);
		} catch (Exception e) {
			throw mapException(e);
		}
	}

}
