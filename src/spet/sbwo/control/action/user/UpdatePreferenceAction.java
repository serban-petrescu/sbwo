package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserPreference;

public class UpdatePreferenceAction extends BasePreferenceAction<UserPreferenceChannel> {

	@Override
	public UserPreferenceChannel doRun(UserPreferenceChannel input, IDatabaseExecutor executor, User user)
			throws ControlException, DatabaseException {
		UserPreference preference = user.getPreference();
		if (user.getPreference() == null) {
			preference = new UserPreference();
			preference.setUser(user);
			user.setPreference(preference);
			executor.create(preference);
		}
		preference.setDraftResumeDelay(input.getDraftResumeDelay());
		preference.setLanguage(input.getLanguage());
		preference.setTheme(input.getTheme());
		return mapToChannel(preference);
	}

}
