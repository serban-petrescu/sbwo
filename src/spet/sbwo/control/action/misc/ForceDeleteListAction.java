package spet.sbwo.control.action.misc;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.action.bo.base.DeleteEntityAction;
import spet.sbwo.control.action.bo.person.DeletePersonAction;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.EntityType;
import spet.sbwo.data.table.User;

public class ForceDeleteListAction extends BaseUserDatabaseAction<List<TrashChannel>, Void> {

	public ForceDeleteListAction() {
		super(TrashChannel.class, true);
	}

	@Override
	public Void doRun(List<TrashChannel> input, IDatabaseExecutor executor, User user) throws ControlException {
		for (TrashChannel c : input) {
			getActionFor(c.getTypeAsEnum()).run(c.getId(), executor, user);
		}
		return null;
	}

	protected DeleteEntityAction<?> getActionFor(EntityType type) {
		// TODO add other entities
		return new DeletePersonAction();
	}

}
