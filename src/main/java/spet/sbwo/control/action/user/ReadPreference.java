package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserPreference;

public class ReadPreference extends BasePreferenceAction<Void> {

	@Override
	public UserPreferenceChannel doRun(Void input, IDatabaseExecutor executor, User user)
			throws ControlException, DatabaseException {
		UserPreference preference = user.getPreference();
		if (user.getPreference() == null) {
			return new CreateDefaultPreference().run(null, executor, user);
		} else {
			return mapToChannel(preference);
		}
	}

}
