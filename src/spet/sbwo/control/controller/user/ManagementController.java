package spet.sbwo.control.controller.user;

import static spet.sbwo.control.controller.user.PreferenceController.createDefaultPreference;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserPreference;
import spet.sbwo.data.view.UserPlain;

public class ManagementController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(ManagementController.class);
	private static final String INITIAL_CREDENT = "init";
	private static final String REGISTER_ERROR = "Unable to register user because of a database error.";
	private static final String ACTIVATE_ERROR = "Unable to activate user because of a database error.";
	private static final String CHANGE_ERROR = "Unable to change user password because of a database error.";
	private static final String READ_ALL_ERROR = "Unable to read all users.";

	private final PasswordHasher hasher = new PasswordHasher();

	public ManagementController(IDatabaseExecutorCreator database) {
		super(database, UserChannel.class);
	}

	public void registerUser(String username) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			if (user != null) {
				LOG.warn("Attempted to create already existing user ({}).", username);
				throw new ControlException(ControlError.INVALID_PROPERTY_VALUE, UserChannel.class);
			} else {
				PasswordHasher.HashedPasswordInfo pwdInfo = this.hasher.hashPassword(INITIAL_CREDENT);
				user = new User();
				UserPreference preference = createDefaultPreference(user);
				user.setUsername(username);
				user.setPassword(pwdInfo.getHash());
				user.setSalt(pwdInfo.getSalt());
				user.setActive(true);
				executor.create(user);
				executor.create(preference);
				executor.commit();
			}
			return null;
		};
		execute(username, REGISTER_ERROR, false, action);
	}

	public void activateUser(String username, boolean active) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			user.setActive(active);
			executor.update(user);
			executor.commit();
			return null;
		};
		execute(username, ACTIVATE_ERROR, action);
	}

	public void resetUserPassword(String username) throws ControlException {
		this.changeUserPassword(username, INITIAL_CREDENT);
	}

	public void changeUserPassword(String username, String password) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			PasswordHasher.HashedPasswordInfo pwdInfo = this.hasher.hashPassword(password);
			user.setPassword(pwdInfo.getHash());
			user.setSalt(pwdInfo.getSalt());
			executor.update(user);
			executor.commit();
			return null;
		};
		execute(username, CHANGE_ERROR, action);
	}

	public List<UserPlain> listAllPlains() throws ControlException {
		return execute(READ_ALL_ERROR, e -> e.select(UserPlain.class).execute());
	}

}
