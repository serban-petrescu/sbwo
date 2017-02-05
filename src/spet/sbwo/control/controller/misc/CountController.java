package spet.sbwo.control.controller.misc;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.access.WhereOperator;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.view.DeletedEntity;

public class CountController extends BaseController {
	private static final String READ_ERROR = "Unable to retrieve counts.";

	public CountController(IDatabaseExecutorCreator database) {
		super(database, CountChannel.class);
	}

	public CountChannel readCounts() throws ControlException {
		IAction<CountChannel> action = executor -> {
			CountChannel result = new CountChannel();
			result.setDeleted(executor.count(DeletedEntity.class).execute());
			result.setPerson(executor.count(Person.class).where("deleted", WhereOperator.EQ, false).execute());
			return result;
		};
		return execute(READ_ERROR, action);
	}

}
