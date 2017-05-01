package spet.sbwo.control.action.misc;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.action.bo.expertise.DeleteExpertise;
import spet.sbwo.control.action.bo.person.DeletePerson;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.EntityType;
import spet.sbwo.data.table.User;

public class ForceDeleteList extends BaseUserDatabaseAction<List<TrashChannel>, Void> {

	public ForceDeleteList() {
		super(TrashChannel.class, true);
	}

	@Override
	public Void doRun(List<TrashChannel> input, IDatabaseExecutor executor, User user) throws ControlException {
		for (TrashChannel c : input) {
			getActionFor(c.getType()).run(c.getId(), executor, user);
		}
		return null;
	}

	protected DeleteEntity<?> getActionFor(EntityType type) {
		if (type == EntityType.PERSON) {
			return new DeletePerson();
		} else {
			return new DeleteExpertise();
		}
	}

}
