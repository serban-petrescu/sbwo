package spet.sbwo.api.service.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.controller.user.FavouriteController;

@Path("/user/favourites")
public class FavouriteService extends BaseService implements IPrivate {
	private final FavouriteController controller;

	public FavouriteService(FavouriteController controller) {
		this.controller = controller;
	}

	@GET
	@Path("/read")
	@Produces("application/json")
	public List<UserFavouriteChannel> readFavourites() {
		try {
			return controller.readFavourites(currentUsername());
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")
	public List<UserFavouriteChannel> updateFavourites(List<UserFavouriteChannel> input) {
		try {
			return controller.updateFavourites(currentUsername(), input);
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@POST
	@Path("/create")
	@Produces("application/json")
	@Consumes("application/json")
	public UserFavouriteChannel createFavourite(UserFavouriteChannel data) {
		try {
			controller.addFavourite(currentUsername(), data);
			return data;
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
