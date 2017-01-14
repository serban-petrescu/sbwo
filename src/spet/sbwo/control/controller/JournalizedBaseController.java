package spet.sbwo.control.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import spet.sbwo.control.ControlException;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public abstract class JournalizedBaseController extends BaseController {

	public JournalizedBaseController(DatabaseFacade database) {
		super(database);
	}

	protected void setCreatedFields(JournalizedBaseEntity entity, String username, IDatabaseExecutor executor)
			throws ControlException {
		User author = getUserByUsername(username, executor);
		entity.setCreatedBy(author);
		entity.setCreatedOn(new Timestamp(new Date().getTime()));
	}

	protected void setChangedFields(JournalizedBaseEntity entity, String username, IDatabaseExecutor executor)
			throws ControlException {
		User author = getUserByUsername(username, executor);
		entity.setChangedBy(author);
		entity.setChangedOn(new Timestamp(new Date().getTime()));
	}

	protected void doEntityDeletion(JournalizedBaseEntity entity, String username, boolean force,
			IDatabaseExecutor executor) throws DatabaseException, ControlException {
		if (force || TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - entity.getCreatedOn().getTime()) < 30) {
			executor.delete(entity);
		} else {
			this.setChangedFields(entity, username, executor);
			entity.setDeleted(true);
		}
	}

	protected void doEntityRestore(JournalizedBaseEntity entity, String username, IDatabaseExecutor executor)
			throws DatabaseException, ControlException {
		this.setChangedFields(entity, username, executor);
		entity.setDeleted(false);
	}

}
