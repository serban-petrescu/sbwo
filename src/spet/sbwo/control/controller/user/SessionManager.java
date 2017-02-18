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

public class SessionManager extends BaseActionExecutor {

	public SessionManager(DatabaseFacade database) {
		super(database);
	}

	public boolean exists(String id) {
		return suppress(() -> execute(new SessionExists(), id), false);
	}

	public UserSession read(String id) {
		return suppress(() -> execute(new ReadSession(), id), null);
	}

	public boolean remove(String id) {
		return suppress(() -> executeAndCommit(new RemoveSession(), id), false);
	}

	public void upsert(UserSession session) {
		suppress(() -> executeAndCommit(new UpsertSession(), session));
	}

	public List<String> readAllExpired(long ts) {
		return suppress(() -> execute(new ReadAllExpired(), ts), Collections.emptyList());
	}
}
