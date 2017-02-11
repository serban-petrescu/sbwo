package spet.sbwo.control.action.bo.base;

import java.sql.Timestamp;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.JournalChannel;
import spet.sbwo.control.mapper.BaseMapper;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public abstract class CreateEntityAction<T extends JournalizedBaseEntity, C extends JournalChannel>
		extends BaseUserDatabaseAction<C, Integer> {
	protected final Class<T> entity;

	protected CreateEntityAction(Class<T> entity, Class<C> channel) {
		super(channel, true);
		this.entity = entity;
	}

	@Override
	public Integer doRun(C input, IDatabaseExecutor executor, User user) throws ControlException, DatabaseException {
		BaseMapper<T, C> mapper = mapper(executor);
		T t = mapper.toInternal(input);
		t.setCreatedBy(user);
		t.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		mapper.flush();
		return t.getId();
	}

	protected abstract BaseMapper<T, C> mapper(IDatabaseExecutor executor);

}
