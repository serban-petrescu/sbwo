package spet.sbwo.control.action.user;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.mapper.UserFavouriteMapper;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class UpdateFavouritesAction
		extends BaseUserDatabaseAction<List<UserFavouriteChannel>, List<UserFavouriteChannel>> {

	public UpdateFavouritesAction() {
		super(UserFavouriteChannel.class, true);
	}

	@Override
	public List<UserFavouriteChannel> doRun(List<UserFavouriteChannel> input, IDatabaseExecutor executor, User user)
			throws ControlException, DatabaseException {
		UserFavouriteMapper mapper = new UserFavouriteMapper(executor, user);
		user.setFavourites(mapper.merge(user.getFavourites(), input));
		mapper.flush();
		return mapper.toExternal(user.getFavourites());
	}

}
