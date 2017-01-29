package spet.sbwo.control.helper;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.control.mapper.UserFavouriteMapper;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserFavourite;

public class UserFavouriteHelper extends BaseController {

	public List<UserFavouriteChannel> readFavourites(IDatabaseExecutor executor, String username)
			throws ControlException {
		User user = this.getUserByUsername(executor, username);
		return new UserFavouriteMapper(executor, user).toExternal(user.getFavourites());
	}

	public void deleteFavourite(IDatabaseExecutor executor, String username, int id)
			throws ControlException, DatabaseException {
		User user = this.getUserByUsername(executor, username);
		UserFavourite favourite = executor.find(UserFavourite.class, id);
		if (favourite != null) {
			user.getFavourites().remove(favourite);
			executor.delete(favourite);
			executor.commit();
		}
	}

	public void addFavourite(IDatabaseExecutor executor, String username, UserFavouriteChannel data)
			throws ControlException, DatabaseException {
		User user = this.getUserByUsername(executor, username);
		UserFavourite favourite = new UserFavouriteMapper(executor, user).toInternal(data);
		user.getFavourites().add(favourite);
		executor.create(favourite);
		executor.commit();
		data.setId(favourite.getId());
	}

	public List<UserFavouriteChannel> updateFavourites(IDatabaseExecutor executor, String username,
			List<UserFavouriteChannel> input) throws ControlException, DatabaseException {
		User user = this.getUserByUsername(executor, username);
		UserFavouriteMapper mapper = new UserFavouriteMapper(executor, user);
		user.setFavourites(mapper.merge(user.getFavourites(), input));
		mapper.flush();
		executor.commit();
		return mapper.toExternal(user.getFavourites());
	}
}
