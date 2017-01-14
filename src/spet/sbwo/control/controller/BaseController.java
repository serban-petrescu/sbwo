package spet.sbwo.control.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.WhereOperator;
import spet.sbwo.data.table.User;

public class BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

	protected DatabaseFacade database;

	public BaseController(DatabaseFacade database) {
		super();
		this.database = database;
	}

	User getUserByUsername(String username, IDatabaseExecutor executor) throws ControlException {
		try {
			return executor.selectSingle(User.class).where("username", WhereOperator.EQ, username).execute();
		} catch (DatabaseException e) {
			LOG.error("Unable to get user by username.");
			throw new ControlException(e, UserChannel.class);
		}
	}
}
