package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public abstract class BasePasswordMatchesAction extends BaseUserDatabaseAction<String, Boolean> {
    protected static final int MAX_LOGIN_FAILS = 3;

    protected BasePasswordMatchesAction() {
        super(UserChannel.class, true);
    }

    @Override
    public Boolean doRun(String input, IDatabaseExecutor executor, User user) {
        boolean result;
        if (user == null || !user.isActive()) {
            return false;
        } else {
            result = checkPassword(user, input);
        }
        updateUserAfterLogin(user, result);
        return result;
    }

    protected abstract boolean checkPassword(User user, String password);

    protected void updateUserAfterLogin(User user, boolean result) {
        if (result) {
            user.setFails(0);
        } else {
            int fails = user.getFails() + 1;
            if (fails >= MAX_LOGIN_FAILS) {
                user.setFails(0);
                user.setActive(false);
            } else {
                user.setFails(fails);
            }
        }
    }

}
