package spet.sbwo.control.controller.user;

import java.util.Collections;
import java.util.List;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.session.SessionExists;
import spet.sbwo.control.action.session.ReadSession;
import spet.sbwo.control.action.session.ReadAllExpired;
import spet.sbwo.control.action.session.RemoveSession;
import spet.sbwo.control.action.session.UpsertSession;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.table.UserSession;

public class SessionManager extends BaseActionExecutor implements ISessionManager {

	public SessionManager(DatabaseFacade database) {
		super(database);
	}

	@Override
	public boolean exists(String id) {
		return suppress(() -> execute(new SessionExists(), id), false);
	}

	@Override
	public UserSession read(String id) {
		return suppress(() -> execute(new ReadSession(), id), null);
	}

	@Override
	public boolean remove(String id) {
		return suppress(() -> executeAndCommit(new RemoveSession(), id), false);
	}

	@Override
	public void upsert(UserSession session) {
		suppress(() -> executeAndCommit(new UpsertSession(), session));
	}

	@Override
	public List<String> readAllExpired(long ts) {
		return suppress(() -> execute(new ReadAllExpired(), ts), Collections.emptyList());
	}
}
