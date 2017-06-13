package spet.sbwo.control.action.session;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.misc.SessionChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.UserSession;

public class SessionExists extends BaseDatabaseAction<String, Boolean> {

    public SessionExists() {
        super(SessionChannel.class);
    }

    @Override
    public Boolean doRun(String input, IDatabaseExecutor executor) {
        return executor.find(UserSession.class, input) != null;
    }

}
