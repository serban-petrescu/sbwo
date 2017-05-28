package spet.sbwo.control.action.base;

import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.Texts;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;

public abstract class BaseDatabaseAction<I, O> implements IDatabaseAction<I, O> {
	protected final Class<?> channel;

	protected BaseDatabaseAction(Class<?> channel) {
		this.channel = channel;
	}

	@Override
	public O run(I input, IDatabaseExecutor executor)  {
		try {
			return doRun(input, executor);
		} catch (DatabaseException e) {
			LoggerFactory.getLogger(this.getClass()).error(Texts.INSTANCE.get(this.getClass()), String.valueOf(input));
			throw new ControlException(e, channel);
		}
	}

	protected abstract O doRun(I input, IDatabaseExecutor executor) ;

}
