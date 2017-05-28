package spet.sbwo.control.action.user;

import java.util.List;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.mapper.UserFavouriteMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class ReadFavourites extends BaseUserDatabaseAction<Void, List<UserFavouriteChannel>> {

    public ReadFavourites() {
        super(UserFavouriteChannel.class, true);
    }

    @Override
    public List<UserFavouriteChannel> doRun(Void input, IDatabaseExecutor e, User u) {
        return new UserFavouriteMapper(e, u).toExternal(u.getFavourites());
    }

}
