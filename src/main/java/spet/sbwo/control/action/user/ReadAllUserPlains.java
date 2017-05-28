package spet.sbwo.control.action.user;

import java.util.List;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.view.UserPlain;

public class ReadAllUserPlains extends BaseDatabaseAction<Void, List<UserPlain>> {

	public ReadAllUserPlains() {
		super(UserChannel.class);
	}

	@Override
	public List<UserPlain> doRun(Void input, IDatabaseExecutor e)  {
		return e.queryList("UserPlain.readAll", UserPlain.class);
	}

}
