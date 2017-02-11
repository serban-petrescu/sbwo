package spet.sbwo.control.controller.misc;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.Actions;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.misc.ForceDeleteListAction;
import spet.sbwo.control.action.misc.ReadAllTrashAction;
import spet.sbwo.control.action.misc.RestoreListAction;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class TrashController extends BaseActionExecutor {

	public TrashController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public void delete(List<TrashChannel> entities, String username) throws ControlException {
		executeAndCommit(username, new ForceDeleteListAction(), entities);
	}

	public void restore(List<TrashChannel> entities, String username) throws ControlException {
		executeAndCommit(username, new RestoreListAction(), entities);
	}

	public void deleteAll(String username) throws ControlException {
		executeAndCommit(username, Actions.chain(new ReadAllTrashAction(), new ForceDeleteListAction()), null);
	}

}
