package spet.sbwo.control.action.misc;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.query.WhereOperator;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.view.DeletedEntity;

public class CountEntitiesAction extends BaseDatabaseAction<Void, CountChannel> {

	public CountEntitiesAction() {
		super(CountChannel.class);
	}

	@Override
	public CountChannel doRun(Void input, IDatabaseExecutor executor) throws ControlException, DatabaseException {
		CountChannel result = new CountChannel();
		result.setDeleted(executor.count(DeletedEntity.class).execute());
		result.setPerson(executor.count(Person.class).where("deleted", WhereOperator.EQ, false).execute());
		return result;
	}

}
