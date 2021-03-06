package spet.sbwo.control.action.user;

import spet.sbwo.control.channel.user.UserPreferenceChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserPreference;

public class UpdatePreference extends BasePreferenceAction<UserPreferenceChannel> {

    @Override
    public UserPreferenceChannel doRun(UserPreferenceChannel input, IDatabaseExecutor executor, User user) {
        UserPreference preference = user.getPreference();
        if (user.getPreference() == null) {
            preference = new UserPreference();
            user.setPreference(preference);
            executor.create(preference);
        }
        preference.setDraftResumeDelay(input.getDraftResumeDelay());
        preference.setLanguage(input.getLanguage());
        preference.setTheme(input.getTheme());
        return mapToChannel(preference);
    }

}
