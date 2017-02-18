package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.query.WhereOperator;
import spet.sbwo.data.table.User;

public class ReadByUsername extends BaseDatabaseAction<String, User> {

	public ReadByUsername() {
		super(UserChannel.class);
	}

	@Override
	public User doRun(String input, IDatabaseExecutor executor) throws DatabaseException, ControlException {
		return executor.selectSingle(User.class).where("username", WhereOperator.EQ, input).execute();
	}

}
