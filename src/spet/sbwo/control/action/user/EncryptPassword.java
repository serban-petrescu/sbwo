package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class EncryptPassword extends BaseUserDatabaseAction<String, String> {
	private static final PasswordHasher HASHER = new PasswordHasher();

	public EncryptPassword() {
		super(UserChannel.class, false);
	}

	@Override
	public String doRun(String input, IDatabaseExecutor executor, User u) throws ControlException {
		return u == null ? null : HASHER.hashPassword(input, u.getSalt());
	}

}
