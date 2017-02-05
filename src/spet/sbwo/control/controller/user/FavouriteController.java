package spet.sbwo.control.controller.user;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.control.mapper.UserFavouriteMapper;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.UserFavourite;

public class FavouriteController extends BaseController {
	private static final String READ_ERROR = "Unable to read user favourites.";
	private static final String UPDATE_ERROR = "Unable to update user favourites.";
	private static final String DELETE_ERROR = "Unable to delete user favourite.";
	private static final String ADD_ERROR = "Unable to add favourite.";

	public FavouriteController(IDatabaseExecutorCreator database) {
		super(database, UserFavouriteChannel.class);
	}

	public List<UserFavouriteChannel> readFavourites(String username) throws ControlException {
		return execute(username, READ_ERROR, (e, u) -> new UserFavouriteMapper(e, u).toExternal(u.getFavourites()));
	}

	public void deleteFavourite(String username, int id) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			UserFavourite favourite = executor.find(UserFavourite.class, id);
			if (favourite != null) {
				user.getFavourites().remove(favourite);
				executor.delete(favourite);
				executor.commit();
			}
			return null;
		};
		execute(username, DELETE_ERROR, action);
	}

	public void addFavourite(String username, UserFavouriteChannel data) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			UserFavourite favourite = new UserFavouriteMapper(executor, user).toInternal(data);
			user.getFavourites().add(favourite);
			executor.create(favourite);
			executor.commit();
			data.setId(favourite.getId());
			return null;
		};
		execute(username, ADD_ERROR, action);
	}

	public List<UserFavouriteChannel> updateFavourites(String username, List<UserFavouriteChannel> input)
			throws ControlException {
		IUserAction<List<UserFavouriteChannel>> action = (executor, user) -> {
			UserFavouriteMapper mapper = new UserFavouriteMapper(executor, user);
			user.setFavourites(mapper.merge(user.getFavourites(), input));
			mapper.flush();
			executor.commit();
			return mapper.toExternal(user.getFavourites());
		};
		return execute(username, UPDATE_ERROR, action);
	}

}
