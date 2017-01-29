package spet.sbwo.control.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserPreference;

public class UserHelper extends BaseController {
	private static final int DEFAULT_DRAFT_RESUME_DELAY = 30;
	private static final String DEFAULT_LANGUAGE = "en";
	private static final String DEFAULT_THEME = "sap_belize";
	private static final Logger LOG = LoggerFactory.getLogger(UserHelper.class);
	private final PasswordHasher hasher = new PasswordHasher();

	public boolean userExists(IDatabaseExecutor executor, String username) throws ControlException {
		User user = this.getUserByUsername(executor, username);
		return user != null && user.isActive();
	}

	public String encryptPassword(IDatabaseExecutor executor, String username, String input) throws ControlException {
		User user = this.getUserByUsername(executor, username);
		return hasher.hashPassword(input, user.getSalt());
	}

	public boolean passwordMatches(IDatabaseExecutor executor, String username, String password, boolean encrypted)
			throws ControlException {
		User user = getUserByUsername(executor, username);
		if (user == null || !user.isActive()) {
			return false;
		} else if (encrypted) {
			return user.getPassword().equals(password);
		} else {
			return hasher.checkPassword(password, user.getPassword(), user.getSalt());
		}
	}

	public void registerUser(IDatabaseExecutor executor, String username, String init)
			throws ControlException, DatabaseException {
		if (userExists(executor, username)) {
			LOG.warn("Attempted to create already existing user ({}).", username);
			throw new ControlException(ControlError.INVALID_PROPERTY_VALUE, UserChannel.class);
		} else {
			PasswordHasher.HashedPasswordInfo pwdInfo = this.hasher.hashPassword(init);
			User user = new User();
			UserPreference preference = createDefaultPreference(user);
			user.setUsername(username);
			user.setPassword(pwdInfo.getHash());
			user.setSalt(pwdInfo.getSalt());
			user.setActive(true);
			executor.create(user);
			executor.create(preference);
			executor.commit();
		}
	}

	public void activateUser(IDatabaseExecutor executor, String username, boolean active)
			throws ControlException, DatabaseException {
		User user = this.getUserByUsername(executor, username);
		user.setActive(active);
		executor.update(user);
		executor.commit();
	}

	public void changePassword(IDatabaseExecutor executor, String username, String password)
			throws ControlException, DatabaseException {
		User user = this.getUserByUsername(executor, username);
		PasswordHasher.HashedPasswordInfo pwdInfo = this.hasher.hashPassword(password);
		user.setPassword(pwdInfo.getHash());
		user.setSalt(pwdInfo.getSalt());
		executor.update(user);
		executor.commit();
	}

	public UserPreferenceChannel readPreferences(IDatabaseExecutor executor, String username)
			throws ControlException, DatabaseException {
		User user = getUserByUsername(executor, username);
		UserPreference preference = user.getPreference();
		if (user.getPreference() == null) {
			preference = createDefaultPreference(user);
			executor.create(preference);
			executor.commit();
		}
		return mapToChannel(preference);
	}

	public UserPreferenceChannel updatePreference(IDatabaseExecutor executor, String username,
			UserPreferenceChannel data) throws ControlException, DatabaseException {
		User user = getUserByUsername(executor, username);
		UserPreference preference = user.getPreference();
		if (user.getPreference() == null) {
			preference = new UserPreference();
			preference.setUser(user);
			user.setPreference(preference);
			executor.create(preference);
		}
		preference.setDraftResumeDelay(data.getDraftResumeDelay());
		preference.setLanguage(data.getLanguage());
		preference.setTheme(data.getTheme());
		executor.commit();
		return mapToChannel(preference);
	}

	protected UserPreferenceChannel mapToChannel(UserPreference preference) {
		UserPreferenceChannel channel = new UserPreferenceChannel();
		channel.setDraftResumeDelay(preference.getDraftResumeDelay());
		channel.setLanguage(preference.getLanguage());
		channel.setTheme(preference.getTheme());
		return channel;
	}

	protected UserPreference createDefaultPreference(User user) {
		UserPreference preference = new UserPreference();
		preference.setUser(user);
		preference.setDraftResumeDelay(DEFAULT_DRAFT_RESUME_DELAY);
		preference.setLanguage(DEFAULT_LANGUAGE);
		preference.setTheme(DEFAULT_THEME);
		user.setPreference(preference);
		return preference;
	}
}
