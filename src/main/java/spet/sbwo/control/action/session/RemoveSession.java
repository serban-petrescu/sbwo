package spet.sbwo.control.action.session;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.SessionChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.UserSession;

public class RemoveSession extends BaseDatabaseAction<String, Boolean> {

    public RemoveSession() {
        super(SessionChannel.class);
    }

    @Override
    public Boolean doRun(String input, IDatabaseExecutor executor) {
        UserSession session = executor.find(UserSession.class, input);
        if (session != null) {
            executor.delete(session);
            return true;
        } else {
            return false;
        }
    }

}
