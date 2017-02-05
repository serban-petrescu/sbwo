package spet.sbwo.control.controller.bo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public abstract class JournalizedBaseController extends BaseController {
	protected final int directDeleteInterval;

	public JournalizedBaseController(IDatabaseExecutorCreator database, Class<?> channel, int directDeleteInterval) {
		super(database, channel);
		this.directDeleteInterval = directDeleteInterval;
	}

	protected void setCreatedFields(JournalizedBaseEntity entity, User author, IDatabaseExecutor executor)
			throws ControlException {
		entity.setCreatedBy(author);
		entity.setCreatedOn(new Timestamp(new Date().getTime()));
	}

	protected void setChangedFields(JournalizedBaseEntity entity, User author, IDatabaseExecutor executor)
			throws ControlException {
		entity.setChangedBy(author);
		entity.setChangedOn(new Timestamp(new Date().getTime()));
	}

	protected void doEntityDeletion(JournalizedBaseEntity entity, User author, boolean force,
			IDatabaseExecutor executor) throws DatabaseException, ControlException {
		if (force || shouldDeleteDirectly(entity)) {
			executor.delete(entity);
		} else {
			setChangedFields(entity, author, executor);
			entity.setDeleted(true);
		}
	}

	protected void doEntityRestore(JournalizedBaseEntity entity, User author, IDatabaseExecutor executor)
			throws DatabaseException, ControlException {
		setChangedFields(entity, author, executor);
		entity.setDeleted(false);
	}

	protected boolean shouldDeleteDirectly(JournalizedBaseEntity entity) {
		long millis = System.currentTimeMillis() - entity.getCreatedOn().getTime();
		return TimeUnit.MILLISECONDS.toMinutes(millis) < this.directDeleteInterval;
	}

}
