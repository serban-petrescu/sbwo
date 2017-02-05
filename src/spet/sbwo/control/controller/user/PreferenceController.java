package spet.sbwo.control.controller.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserPreference;

public class PreferenceController extends BaseController {
	private static final int DEFAULT_DRAFT_RESUME_DELAY = 30;
	private static final String DEFAULT_LANGUAGE = "en";
	private static final String DEFAULT_THEME = "sap_belize";
	private static final String READ_ERROR = "Unable to read user preference.";
	private static final String UPDATE_ERROR = "Unable to update user preference.";
	
	public PreferenceController(IDatabaseExecutorCreator database) {
		super(database, UserPreferenceChannel.class);
	}

	public UserPreferenceChannel readPreference(String username) throws ControlException {
		IUserAction<UserPreferenceChannel> action = (executor, user) -> {
			UserPreference preference = user.getPreference();
			if (user.getPreference() == null) {
				preference = createDefaultPreference(user);
				executor.create(preference);
				executor.commit();
			}
			return mapToChannel(preference);
		};
		return execute(username, READ_ERROR, action);
	}

	public UserPreferenceChannel updatePreference(String username, UserPreferenceChannel data) throws ControlException {
		IUserAction<UserPreferenceChannel> action = (executor, user) -> {
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
		};
		return execute(username, UPDATE_ERROR, action);
	}

	protected static UserPreferenceChannel mapToChannel(UserPreference preference) {
		UserPreferenceChannel channel = new UserPreferenceChannel();
		channel.setDraftResumeDelay(preference.getDraftResumeDelay());
		channel.setLanguage(preference.getLanguage());
		channel.setTheme(preference.getTheme());
		return channel;
	}
	
	protected static UserPreference createDefaultPreference(User user) {
		UserPreference preference = new UserPreference();
		preference.setUser(user);
		preference.setDraftResumeDelay(DEFAULT_DRAFT_RESUME_DELAY);
		preference.setLanguage(DEFAULT_LANGUAGE);
		preference.setTheme(DEFAULT_THEME);
		user.setPreference(preference);
		return preference;
	}
}
