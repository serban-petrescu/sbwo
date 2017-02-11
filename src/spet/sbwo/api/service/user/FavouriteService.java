package spet.sbwo.api.service.user;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.controller.user.FavouriteController;

@Path("/user/favourites")
public class FavouriteService extends BaseService {
	private final FavouriteController controller;
	private final ObjectWriter favWriter;
	private final ObjectReader favReader;
	private final ObjectWriter favListWriter;
	private final ObjectReader favListReader;

	public FavouriteService(FavouriteController controller) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.controller = controller;
		this.favReader = mapper.readerFor(UserFavouriteChannel.class);
		this.favWriter = mapper.writerFor(UserFavouriteChannel.class);
		CollectionType favListType = mapper.getTypeFactory().constructCollectionType(List.class,
				UserFavouriteChannel.class);
		this.favListReader = mapper.readerFor(favListType);
		this.favListWriter = mapper.writerFor(favListType);
	}

	@GET
	@Path("/read")
	@Produces("application/json")
	public String readFavourites() {
		try {
			List<UserFavouriteChannel> favourites = controller.readFavourites(currentUsername());
			return this.favListWriter.writeValueAsString(favourites);
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")
	public String updateFavourites(InputStream body) {
		try {
			List<UserFavouriteChannel> input = this.favListReader.readValue(body);
			List<UserFavouriteChannel> favourites = controller.updateFavourites(currentUsername(), input);
			return this.favListWriter.writeValueAsString(favourites);
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@POST
	@Path("/create")
	@Produces("application/json")
	@Consumes("application/json")
	public String createFavourite(InputStream body) {
		try {
			UserFavouriteChannel data = this.favReader.readValue(body);
			controller.addFavourite(currentUsername(), data);
			return this.favWriter.writeValueAsString(data);
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@DELETE
	@Path("/delete/{id}")
	public void deleteFavourite(@PathParam("id") int id) {
		try {
			controller.deleteFavourite(currentUsername(), id);
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
