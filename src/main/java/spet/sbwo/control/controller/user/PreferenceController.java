package spet.sbwo.control.controller.user;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.ReadPreference;
import spet.sbwo.control.action.user.UpdatePreference;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class PreferenceController extends BaseActionExecutor {

    public PreferenceController(IDatabaseExecutorCreator database) {
        super(database);
    }

    public UserPreferenceChannel readPreference(String username) {
        return executeAndCommit(username, new ReadPreference(), null);
    }

    public UserPreferenceChannel updatePreference(String username, UserPreferenceChannel data) {
        return executeAndCommit(username, new UpdatePreference(), data);
    }

}
