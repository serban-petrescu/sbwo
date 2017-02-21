package spet.sbwo.control.action.bo.base;

import java.time.LocalDateTime;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.JournalChannel;
import spet.sbwo.control.mapper.BaseMapper;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public abstract class CreateEntity<T extends JournalizedBaseEntity, C extends JournalChannel>
		extends BaseUserDatabaseAction<C, T> {
	protected final Class<T> entity;

	protected CreateEntity(Class<T> entity, Class<C> channel) {
		super(channel, true);
		this.entity = entity;
	}

	@Override
	public T doRun(C input, IDatabaseExecutor executor, User user) throws ControlException, DatabaseException {
		BaseMapper<T, C> mapper = mapper(executor);
		T t = mapper.toInternal(input);
		t.setCreatedBy(user);
		t.setCreatedOn(LocalDateTime.now());
		mapper.flush();
		return t;
	}

	protected abstract BaseMapper<T, C> mapper(IDatabaseExecutor executor);

}
