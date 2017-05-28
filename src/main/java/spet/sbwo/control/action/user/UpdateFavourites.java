package spet.sbwo.control.action.user;

import java.util.List;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.mapper.UserFavouriteMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class UpdateFavourites
		extends BaseUserDatabaseAction<List<UserFavouriteChannel>, List<UserFavouriteChannel>> {

	public UpdateFavourites() {
		super(UserFavouriteChannel.class, true);
	}

	@Override
	public List<UserFavouriteChannel> doRun(List<UserFavouriteChannel> input, IDatabaseExecutor executor, User user)
			 {
		UserFavouriteMapper mapper = new UserFavouriteMapper(executor, user);
		user.setFavourites(mapper.merge(user.getFavourites(), input));
		mapper.flush();
		return mapper.toExternal(user.getFavourites());
	}

}
