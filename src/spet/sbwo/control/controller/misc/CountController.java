package spet.sbwo.control.controller.misc;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.misc.CountEntitiesAction;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class CountController extends BaseActionExecutor {

	public CountController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public CountChannel readCounts() throws ControlException {
		return execute(new CountEntitiesAction(), null);
	}

}
