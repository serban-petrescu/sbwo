package spet.sbwo.control.action.session;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.SessionChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.query.WhereOperator;
import spet.sbwo.data.table.UserSession;

public class ReadAllExpired extends BaseDatabaseAction<Long, List<String>> {

	public ReadAllExpired() {
		super(SessionChannel.class);
	}

	@Override
	public List<String> doRun(Long input, IDatabaseExecutor executor) throws ControlException, DatabaseException {
		return executor.select(UserSession.class, String.class, "id").where("expiryTime", WhereOperator.LE, input)
				.execute();
	}

}
