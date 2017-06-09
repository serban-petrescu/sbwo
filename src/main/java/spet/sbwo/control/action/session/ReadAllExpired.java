package spet.sbwo.control.action.session;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.misc.SessionChannel;
import spet.sbwo.data.access.IDatabaseExecutor;

import java.util.List;

public class ReadAllExpired extends BaseDatabaseAction<Long, List<String>> {

    public ReadAllExpired() {
        super(SessionChannel.class);
    }

    @Override
    public List<String> doRun(Long input, IDatabaseExecutor executor) {
        return executor.queryList("UserSession.readAllExpiredIds", String.class, input);
    }

}
