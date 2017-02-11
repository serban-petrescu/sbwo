package spet.sbwo.control.controller.user;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.AddFavouriteAction;
import spet.sbwo.control.action.user.DeleteFavouriteAction;
import spet.sbwo.control.action.user.ReadFavouritesAction;
import spet.sbwo.control.action.user.UpdateFavouritesAction;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class FavouriteController extends BaseActionExecutor {

	public FavouriteController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public List<UserFavouriteChannel> readFavourites(String username) throws ControlException {
		return execute(username, new ReadFavouritesAction(), null);
	}

	public void deleteFavourite(String username, int id) throws ControlException {
		executeAndCommit(username, new DeleteFavouriteAction(), id);
	}

	public void addFavourite(String username, UserFavouriteChannel data) throws ControlException {
		executeAndCommit(username, new AddFavouriteAction(), data);
	}

	public List<UserFavouriteChannel> updateFavourites(String username, List<UserFavouriteChannel> input)
			throws ControlException {
		return executeAndCommit(username, new UpdateFavouritesAction(), input);
	}

}
