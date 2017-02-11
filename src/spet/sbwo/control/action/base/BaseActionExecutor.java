package spet.sbwo.control.action.base;

import java.util.concurrent.Callable;

import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.user.ReadByUsernameAction;
import spet.sbwo.control.action.user.ReadByUsernameMandatoryAction;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.User;

public abstract class BaseActionExecutor {
	protected final IDatabaseExecutorCreator database;

	protected BaseActionExecutor(IDatabaseExecutorCreator database) {
		this.database = database;
	}

	protected User getUserByUsername(IDatabaseExecutor executor, String username, boolean mandatory)
			throws ControlException {
		return (mandatory ? new ReadByUsernameMandatoryAction() : new ReadByUsernameAction()).run(username, executor);
	}

	protected <I, O> O execute(IDatabaseAction<I, O> action, I in) throws ControlException {
		try (IDatabaseExecutor executor = database.createExecutor()) {
			return action.run(in, executor);
		} catch (DatabaseException e) {
			throw new ControlException(e);
		}
	}

	protected <I, O> O execute(String username, IUserDatabaseAction<I, O> action, I in) throws ControlException {
		try (IDatabaseExecutor executor = database.createExecutor()) {
			return action.run(in, executor, getUserByUsername(executor, username, false));
		} catch (DatabaseException e) {
			throw new ControlException(e);
		}
	}

	protected <I, O> O executeAndCommit(String username, IUserDatabaseAction<I, O> action, I in)
			throws ControlException {
		try (IDatabaseExecutor executor = database.createExecutor()) {
			O o = action.run(in, executor, getUserByUsername(executor, username, false));
			executor.commit();
			return o;
		} catch (DatabaseException e) {
			throw new ControlException(e);
		}
	}

	protected <I, O> O executeAndCommit(IDatabaseAction<I, O> action, I in) throws ControlException {
		try (IDatabaseExecutor executor = database.createExecutor()) {
			O o = action.run(in, executor);
			executor.commit();
			return o;
		} catch (DatabaseException e) {
			throw new ControlException(e);
		}
	}

	protected <T> T suppress(Callable<T> callable) {
		return suppress(callable, null);
	}

	protected <T> T suppress(Callable<T> callable, T defaultValue) {
		try {
			return callable.call();
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error("Supressed exception.", e);
			return defaultValue;
		}
	}
}
