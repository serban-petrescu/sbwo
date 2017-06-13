package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserChannel;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class ChangePassword extends BaseUserDatabaseAction<String, Void> {
    private static final PasswordHasher HASHER = new PasswordHasher();

    public ChangePassword() {
        super(UserChannel.class, true);
    }

    @Override
    public Void doRun(String input, IDatabaseExecutor executor, User user) {
        PasswordHasher.HashedPasswordInfo pwdInfo = HASHER.hashPassword(input);
        user.setPassword(pwdInfo.getHash());
        user.setSalt(pwdInfo.getSalt());
        return null;
    }

}
