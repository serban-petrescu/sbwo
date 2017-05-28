package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.data.table.UserPreference;

public abstract class BasePreferenceAction<I> extends BaseUserDatabaseAction<I, UserPreferenceChannel> {

    protected BasePreferenceAction() {
        super(UserPreferenceChannel.class, true);
    }

    protected UserPreferenceChannel mapToChannel(UserPreference preference) {
        UserPreferenceChannel channel = new UserPreferenceChannel();
        channel.setDraftResumeDelay(preference.getDraftResumeDelay());
        channel.setLanguage(preference.getLanguage());
        channel.setTheme(preference.getTheme());
        return channel;
    }
}
