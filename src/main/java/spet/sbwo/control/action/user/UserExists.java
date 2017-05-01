package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class UserExists extends BaseUserDatabaseAction<Void, Boolean> {

	public UserExists() {
		super(UserChannel.class, false);
	}

	@Override
	public Boolean doRun(Void input, IDatabaseExecutor executor, User user) {
		return user != null;
	}

}
