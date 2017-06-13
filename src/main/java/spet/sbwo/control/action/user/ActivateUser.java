package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class ActivateUser extends BaseUserDatabaseAction<Boolean, Void> {

    public ActivateUser() {
        super(UserChannel.class, true);
    }

    @Override
    public Void doRun(Boolean input, IDatabaseExecutor executor, User user) {
        user.setActive(input);
        return null;
    }

}
