package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserFavouriteChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.user.UserFavouriteMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserFavourite;

import java.util.List;

public class UpdateFavourites
    extends BaseUserDatabaseAction<List<UserFavouriteChannel>, List<UserFavouriteChannel>> {

    public UpdateFavourites() {
        super(UserFavouriteChannel.class, true);
    }

    @Override
    public List<UserFavouriteChannel> doRun(List<UserFavouriteChannel> input, IDatabaseExecutor executor, User user) {
        IMapper<UserFavourite, UserFavouriteChannel> mapper = UserFavouriteMapper.newInstance();
        mapper.mergeIntoEntities(input, user.getFavourites());
        return mapper.toChannels(user.getFavourites());
    }

}
