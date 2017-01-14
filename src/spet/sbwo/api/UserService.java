package spet.sbwo.api;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.control.controller.UserController;

@Path("/user")
public class UserService extends BaseService {
	private UserController userController;
	private ObjectWriter tileWriter;
	private ObjectReader tileReader;
	private ObjectWriter favWriter;
	private ObjectReader favReader;
	private ObjectWriter favListWriter;
	private ObjectReader favListReader;

	public UserService(UserController userController) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.userController = userController;
		this.tileReader = mapper.readerFor(UserHomeTilesChannel.class);
		this.tileWriter = mapper.writerFor(UserHomeTilesChannel.class);
		this.favReader = mapper.readerFor(UserFavouriteChannel.class);
		this.favWriter = mapper.writerFor(UserFavouriteChannel.class);
		CollectionType favListType = mapper.getTypeFactory().constructCollectionType(List.class,
				UserFavouriteChannel.class);
		this.favListReader = mapper.readerFor(favListType);
		this.favListWriter = mapper.writerFor(favListType);
	}

	@GET
	@Path("/tiles/read")
	@Produces("application/json")
	public String readTiles(@Context HttpServletRequest request) {
		try {
			UserHomeTilesChannel data = this.userController.getTiles(getCurrentUsername(request));
			return this.tileWriter.writeValueAsString(data);
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@PUT
	@Path("/tiles/update")
	@Produces("application/json")
	@Consumes("application/json")
	public String updateTiles(@Context HttpServletRequest request, InputStream body) {
		try {
			UserHomeTilesChannel input = this.tileReader.readValue(body);
			UserHomeTilesChannel result = this.userController.updateTiles(input, getCurrentUsername(request));
			return this.tileWriter.writeValueAsString(result);
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@GET
	@Path("/favourites/read")
	@Produces("application/json")
	public String readFavourites(@Context HttpServletRequest request) {
		try {
			List<UserFavouriteChannel> favourites = this.userController.readFavourites(getCurrentUsername(request));
			return this.favListWriter.writeValueAsString(favourites);
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@PUT
	@Path("/favourites/update")
	@Produces("application/json")
	@Consumes("application/json")
	public String updateFavourites(@Context HttpServletRequest request, InputStream body) {
		try {
			List<UserFavouriteChannel> input = this.favListReader.readValue(body);
			List<UserFavouriteChannel> favourites = this.userController.updateFavourites(getCurrentUsername(request),
					input);
			return this.favListWriter.writeValueAsString(favourites);
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@POST
	@Path("/favourites/create")
	@Produces("application/json")
	@Consumes("application/json")
	public String createFavourite(@Context HttpServletRequest request, InputStream body) {
		try {
			UserFavouriteChannel data = this.favReader.readValue(body);
			this.userController.addFavourite(getCurrentUsername(request), data);
			return this.favWriter.writeValueAsString(data);
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@DELETE
	@Path("/favourites/delete/{id}")
	public void deleteFavourite(@Context HttpServletRequest request, @PathParam("id") int id) {
		try {
			this.userController.deleteFavourite(getCurrentUsername(request), id);
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}
}
