package spet.sbwo.control.controller.user;

import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.control.util.ILoginProvider;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.User;

public class LoginController extends BaseController implements ILoginProvider {
	private static final String ENCRYPTION_ERROR = "Failed to encrypt user password.";
	private static final String AUTH_CHECK_ERROR = "Forcing failed authentication because of underlying error (when checking user password).";
	private static final String USER_EXISTANCE_ERROR = "Forcing failed authentication because of underlying error (when checking user existance).";
	private static final int MAX_LOGIN_FAILS = 3;

	private final PasswordHasher hasher = new PasswordHasher();

	public LoginController(IDatabaseExecutorCreator database) {
		super(database, UserChannel.class);
	}

	@Override
	public boolean userExists(String username) {
		IUserAction<Boolean> action = (e, u) -> u != null && u.isActive();
		return supress(() -> execute(username, USER_EXISTANCE_ERROR, false, action), false);
	}

	@Override
	public boolean passwordMatchesPlain(String username, String password) {
		return passwordMatches(username, password, false);
	}

	@Override
	public boolean passwordMatchesEncrypted(String username, String password) {
		return passwordMatches(username, password, true);
	}

	@Override
	public String encryptPassword(String username, String input) {
		IUserAction<String> action = (e, u) -> u == null ? null : hasher.hashPassword(input, u.getSalt());
		return supress(() -> execute(username, ENCRYPTION_ERROR, false, action), null);
	}

	protected boolean passwordMatches(String username, String password, boolean encrypted) {
		IUserAction<Boolean> action = (executor, user) -> {
			boolean result;
			if (user == null || !user.isActive()) {
				return false;
			} else if (encrypted) {
				result = user.getPassword().equals(password);
			} else {
				result = hasher.checkPassword(password, user.getPassword(), user.getSalt());
			}
			updateUserAfterLogin(user, result);
			executor.commit();
			return result;
		};
		return supress(() -> execute(username, AUTH_CHECK_ERROR, false, action), false);
	}

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
