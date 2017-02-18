package spet.sbwo.control.action.session;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.SessionChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.UserSession;

public class ReadSession extends BaseDatabaseAction<String, UserSession> {

	public ReadSession() {
		super(SessionChannel.class);
	}

	@Override
	public UserSession doRun(String input, IDatabaseExecutor executor) throws DatabaseException {
		return executor.find(UserSession.class, input);
	}

}
