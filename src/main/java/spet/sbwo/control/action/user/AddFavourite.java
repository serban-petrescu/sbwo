package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.mapper.UserFavouriteMapper;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserFavourite;

public class AddFavourite extends BaseUserDatabaseAction<UserFavouriteChannel, Void> {

	public AddFavourite() {
		super(UserFavouriteChannel.class, true);
	}

	@Override
	public Void doRun(UserFavouriteChannel input, IDatabaseExecutor executor, User user)
			throws ControlException, DatabaseException {
		UserFavourite favourite = new UserFavouriteMapper(executor, user).toInternal(input);
		user.getFavourites().add(favourite);
		executor.create(favourite);
		input.setId(favourite.getId());
		return null;
	}

}
