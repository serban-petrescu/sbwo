package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.user.UserChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.view.UserPlain;

import java.util.List;

public class ReadAllUserPlains extends BaseDatabaseAction<Void, List<UserPlain>> {

    public ReadAllUserPlains() {
        super(UserChannel.class);
    }

    @Override
    public List<UserPlain> doRun(Void input, IDatabaseExecutor e) {
        return e.queryList("UserPlain.readAll", UserPlain.class);
    }

}
