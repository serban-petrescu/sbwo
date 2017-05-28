package spet.sbwo.control.action.user;

import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserPreference;

public class CreateDefaultPreference extends BasePreferenceAction<Void> {
    private static final int DEFAULT_DRAFT_RESUME_DELAY = 30;
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String DEFAULT_THEME = "sap_belize";

    @Override
    public UserPreferenceChannel doRun(Void input, IDatabaseExecutor executor, User user) {
        UserPreference preference = new UserPreference();
        preference.setUser(user);
        preference.setDraftResumeDelay(DEFAULT_DRAFT_RESUME_DELAY);
        preference.setLanguage(DEFAULT_LANGUAGE);
        preference.setTheme(DEFAULT_THEME);
        user.setPreference(preference);
        executor.create(preference);
        return mapToChannel(preference);
    }

}
