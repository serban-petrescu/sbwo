package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserFavouriteChannel;
import spet.sbwo.control.mapper.user.UserFavouriteMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserFavourite;

public class AddFavourite extends BaseUserDatabaseAction<UserFavouriteChannel, Void> {

    public AddFavourite() {
        super(UserFavouriteChannel.class, true);
    }

    @Override
    public Void doRun(UserFavouriteChannel input, IDatabaseExecutor executor, User user) {
        UserFavourite favourite = UserFavouriteMapper.newInstance().toEntity(input);
        user.getFavourites().add(favourite);
        executor.create(favourite);
        input.setId(favourite.getId());
        return null;
    }

}
