package spet.sbwo.control.controller.user;

import java.util.Collections;
import java.util.List;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.session.ExistsAction;
import spet.sbwo.control.action.session.ReadAction;
import spet.sbwo.control.action.session.ReadExpiredAction;
import spet.sbwo.control.action.session.RemoveAction;
import spet.sbwo.control.action.session.UpsertAction;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.table.UserSession;

public class SessionManager extends BaseActionExecutor {

	public SessionManager(DatabaseFacade database) {
		super(database);
	}

	public boolean exists(String id) {
		return suppress(() -> execute(new ExistsAction(), id), false);
	}

	public UserSession read(String id) {
		return suppress(() -> execute(new ReadAction(), id), null);
	}

	public boolean remove(String id) {
		return suppress(() -> executeAndCommit(new RemoveAction(), id), false);
	}

	public void upsert(UserSession session) {
		suppress(() -> executeAndCommit(new UpsertAction(), session));
	}

	public List<String> readAllExpired(long ts) {
		return suppress(() -> execute(new ReadExpiredAction(), ts), Collections.emptyList());
	}
}
