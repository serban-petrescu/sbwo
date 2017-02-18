package spet.sbwo.control.action.bo.base;

import java.util.concurrent.TimeUnit;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public class DeleteEntity<T extends JournalizedBaseEntity> extends BaseUserBoAction<T, Integer, Void> {
	protected final long directDeleteInterval;

	public DeleteEntity(Class<T> entity, Class<?> channel) {
		this(entity, channel, -1);
	}

	public DeleteEntity(Class<T> entity, Class<?> channel, long directDeleteInterval) {
		super(entity, channel, true);
		this.directDeleteInterval = directDeleteInterval;
	}

	@Override
	protected Void doRun(Integer id, T t, IDatabaseExecutor executor, User user) throws DatabaseException {
		if (directDeleteInterval < 0 || shouldDeleteDirectly(t)) {
			delete(executor, t);
		} else {
			mark(user, t);
		}
		return null;
	}

	protected void delete(IDatabaseExecutor executor, T t) throws DatabaseException {
		executor.delete(t);
	}

	protected void mark(User user, T t) {
		t.setDeleted(true);
		changed(user, t);
	}

	protected boolean shouldDeleteDirectly(JournalizedBaseEntity entity) {
		long millis = System.currentTimeMillis() - entity.getCreatedOn().getTime();
		return TimeUnit.MILLISECONDS.toMinutes(millis) < directDeleteInterval;
	}

	@Override
	protected Integer keyFromInput(Integer input) {
		return input;
	}
}
