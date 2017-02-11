package spet.sbwo.control.controller.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.ReadPreferenceAction;
import spet.sbwo.control.action.user.UpdatePreferenceAction;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class PreferenceController extends BaseActionExecutor {

	public PreferenceController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public UserPreferenceChannel readPreference(String username) throws ControlException {
		return executeAndCommit(username, new ReadPreferenceAction(), null);
	}

	public UserPreferenceChannel updatePreference(String username, UserPreferenceChannel data) throws ControlException {
		return executeAndCommit(username, new UpdatePreferenceAction(), data);
	}

}
