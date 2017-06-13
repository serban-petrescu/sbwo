package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserFavouriteChannel;
import spet.sbwo.control.mapper.user.UserFavouriteMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

import java.util.List;

public class ReadFavourites extends BaseUserDatabaseAction<Void, List<UserFavouriteChannel>> {

    public ReadFavourites() {
        super(UserFavouriteChannel.class, true);
    }

    @Override
    public List<UserFavouriteChannel> doRun(Void input, IDatabaseExecutor e, User u) {
        return UserFavouriteMapper.newInstance().toChannels(u.getFavourites());
    }

}
