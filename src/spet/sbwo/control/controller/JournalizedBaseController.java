package spet.sbwo.control.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import spet.sbwo.control.ControlException;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public abstract class JournalizedBaseController extends BaseMainController {
	protected final int directDeleteInterval;

	public JournalizedBaseController(IDatabaseExecutorCreator database, int directDeleteInterval) {
		super(database);
		this.directDeleteInterval = directDeleteInterval;
	}

	protected void setCreatedFields(JournalizedBaseEntity entity, String username, IDatabaseExecutor executor)
			throws ControlException {
		User author = getUserByUsername(executor, username, false);
		entity.setCreatedBy(author);
		entity.setCreatedOn(new Timestamp(new Date().getTime()));
	}

	protected void setChangedFields(JournalizedBaseEntity entity, String username, IDatabaseExecutor executor)
			throws ControlException {
		User author = getUserByUsername(executor, username, false);
		entity.setChangedBy(author);
		entity.setChangedOn(new Timestamp(new Date().getTime()));
	}

	protected void doEntityDeletion(JournalizedBaseEntity entity, String username, boolean force,
			IDatabaseExecutor executor) throws DatabaseException, ControlException {
		if (force || shouldDeleteDirectly(entity)) {
			executor.delete(entity);
		} else {
			setChangedFields(entity, username, executor);
			entity.setDeleted(true);
		}
	}

	protected void doEntityRestore(JournalizedBaseEntity entity, String username, IDatabaseExecutor executor)
			throws DatabaseException, ControlException {
		this.setChangedFields(entity, username, executor);
		entity.setDeleted(false);
	}

	protected boolean shouldDeleteDirectly(JournalizedBaseEntity entity) {
		long millis = System.currentTimeMillis() - entity.getCreatedOn().getTime();
		return TimeUnit.MILLISECONDS.toMinutes(millis) < this.directDeleteInterval;
	}

}
