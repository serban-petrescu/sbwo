package spet.sbwo.control.controller.misc;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.misc.CountEntities;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class CountController extends BaseActionExecutor {

	public CountController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public CountChannel readCounts()  {
		return execute(new CountEntities(), null);
	}

}
