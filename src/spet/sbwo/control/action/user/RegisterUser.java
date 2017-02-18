package spet.sbwo.control.action.user;

import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class RegisterUser extends BaseUserDatabaseAction<String, Void> {
	private static final String INITIAL_CREDENT = "init";
	private static final PasswordHasher HASHER = new PasswordHasher();

	public RegisterUser() {
		super(UserChannel.class, false);
	}

	@Override
	public Void doRun(String input, IDatabaseExecutor executor, User user) throws ControlException, DatabaseException {
		if (user != null) {
			LoggerFactory.getLogger(getClass()).warn("Attempted to create already existing user ({}).", input);
			throw new ControlException(ControlError.INVALID_PROPERTY_VALUE, UserChannel.class);
		} else {
			PasswordHasher.HashedPasswordInfo pwdInfo = HASHER.hashPassword(INITIAL_CREDENT);
			User newUser = new User();
			newUser.setUsername(input);
			newUser.setPassword(pwdInfo.getHash());
			newUser.setSalt(pwdInfo.getSalt());
			newUser.setActive(true);
			executor.create(newUser);
			new CreateDefaultPreference().run(null, executor, newUser);
		}
		return null;
	}

}
