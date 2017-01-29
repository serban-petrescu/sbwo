package spet.sbwo.control.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.WhereOperator;
import spet.sbwo.data.table.User;

public class BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

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
}
