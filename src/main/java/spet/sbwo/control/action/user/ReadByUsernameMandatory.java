package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.user.UserChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class ReadByUsernameMandatory extends ReadByUsername {

    @Override
    public User doRun(String input, IDatabaseExecutor executor) {
        User u = super.doRun(input, executor);
        if (u == null) {
            throw new ControlException(ControlError.ENTITY_NOT_FOUND, UserChannel.class);
        } else {
            return u;
        }
    }
}
