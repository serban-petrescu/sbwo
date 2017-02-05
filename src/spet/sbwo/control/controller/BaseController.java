package spet.sbwo.control.controller;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.access.WhereOperator;
import spet.sbwo.data.table.User;

public class BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

	protected final Class<?> channel;
	protected final IDatabaseExecutorCreator database;

	public BaseController(IDatabaseExecutorCreator database, Class<?> channel) {
		this.database = database;
		this.channel = channel;
	}

	protected User getUserByUsername(IDatabaseExecutor executor, String username) throws ControlException {
		return getUserByUsername(executor, username, true);
	}

	protected User getUserByUsername(IDatabaseExecutor executor, String username, boolean mandatory)
			throws ControlException {
		try {
			User u = executor.selectSingle(User.class).where("username", WhereOperator.EQ, username).execute();
			if (u == null && mandatory) {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, UserChannel.class);
			} else {
				return u;
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to get user by username.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	protected <T> T execute(IAction<T> action) throws ControlException {
		return execute(null, action);
	}

	protected <T> T execute(String logMessage, IAction<T> action) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return action.run(executor);
		} catch (DatabaseException e) {
			if (logMessage != null) {
				LOG.error(logMessage);
			}
			throw new ControlException(e, channel);
		}
	}

	protected <T> T execute(String username, IUserAction<T> action) throws ControlException {
		return execute(null, username, action);
	}

	protected <T> T execute(String username, String logMessage, IUserAction<T> action) throws ControlException {
		return execute(username, logMessage, true, action);
	}

	protected <T> T execute(String username, String logMessage, boolean userMandatory, IUserAction<T> action)
			throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return action.run(executor, getUserByUsername(executor, username, userMandatory));
		} catch (DatabaseException e) {
			if (logMessage != null) {
				LOG.error(logMessage);
			}
			throw new ControlException(e, channel);
		}
	}

	protected <T> T supress(Callable<T> callable) {
		return supress(callable, null);
	}

	protected <T> T supress(Callable<T> callable, T defaultValue) {
		try {
			return callable.call();
		} catch (Exception e) {
			LOG.error("Supressed exception.", e);
			return defaultValue;
		}
	}

	@FunctionalInterface
	protected static interface IAction<T> {
		T run(IDatabaseExecutor executor) throws DatabaseException, ControlException;
	}

	@FunctionalInterface
	protected static interface IUserAction<T> {
		T run(IDatabaseExecutor executor, User user) throws DatabaseException, ControlException;
	}

}
