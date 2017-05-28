package spet.sbwo.control.action.base;

import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.Texts;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public abstract class BaseUserDatabaseAction<I, O> implements IUserDatabaseAction<I, O> {
	protected final Class<?> channel;
	protected final boolean mandatory;

	protected BaseUserDatabaseAction(Class<?> channel, boolean mandatory) {
		this.channel = channel;
		this.mandatory = mandatory;
	}

	@Override
	public O run(I input, IDatabaseExecutor executor, User user)  {
		try {
			if (mandatory && user == null) {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, UserChannel.class);
			} else {
				return doRun(input, executor, user);
			}
		} catch (DatabaseException e) {
			LoggerFactory.getLogger(this.getClass()).error(Texts.INSTANCE.get(this.getClass()), String.valueOf(input));
			throw new ControlException(e, channel);
		}
	}

	protected abstract O doRun(I input, IDatabaseExecutor executor, User user) ;

}
