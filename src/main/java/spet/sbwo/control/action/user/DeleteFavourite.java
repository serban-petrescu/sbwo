package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserFavourite;

public class DeleteFavourite extends BaseUserDatabaseAction<Integer, Void> {

    public DeleteFavourite() {
        super(UserFavouriteChannel.class, true);
    }

    @Override
    public Void doRun(Integer input, IDatabaseExecutor executor, User user) {
        UserFavourite favourite = executor.find(UserFavourite.class, input);
        if (favourite != null) {
            user.getFavourites().remove(favourite);
            executor.delete(favourite);
        }
        return null;
    }

}
