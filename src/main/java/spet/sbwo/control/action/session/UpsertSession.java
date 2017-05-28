package spet.sbwo.control.action.session;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.SessionChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.UserSession;

public class UpsertSession extends BaseDatabaseAction<UserSession, Void> {
	public UpsertSession() {
		super(SessionChannel.class);
	}

	@Override
	public Void doRun(UserSession input, IDatabaseExecutor executor)  {
		UserSession attached = executor.find(UserSession.class, input.getId());
		if (attached == null) {
			executor.create(input);
		} else {
			executor.update(input);
		}
		return null;
	}
}
