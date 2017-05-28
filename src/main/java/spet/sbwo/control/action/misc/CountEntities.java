package spet.sbwo.control.action.misc;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.data.access.IDatabaseExecutor;

public class CountEntities extends BaseDatabaseAction<Void, CountChannel> {

	public CountEntities() {
		super(CountChannel.class);
	}

	@Override
	public CountChannel doRun(Void input, IDatabaseExecutor executor)  {
		CountChannel result = new CountChannel();
		result.setDeleted(executor.querySingle("DeletedEntity.countAll", Long.class).orElse(0L));
		result.setPerson(executor.querySingle("Person.countByDeleted", Long.class, false).orElse(0L));
		result.setExpertise(executor.querySingle("Expertise.countByDeleted", Long.class, false).orElse(0L));
		return result;
	}

}
