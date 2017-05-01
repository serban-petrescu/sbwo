package spet.sbwo.control.controller.misc;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.misc.ForceDeleteList;
import spet.sbwo.control.action.misc.ReadAllTrash;
import spet.sbwo.control.action.misc.RestoreList;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class TrashController extends BaseActionExecutor {

	public TrashController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public void delete(List<TrashChannel> entities, String username) throws ControlException {
		executeAndCommit(username, new ForceDeleteList(), entities);
	}

	public void restore(List<TrashChannel> entities, String username) throws ControlException {
		executeAndCommit(username, new RestoreList(), entities);
	}

	public void deleteAll(String username) throws ControlException {
		executeAndCommit(username, new ReadAllTrash().then(new ForceDeleteList()), null);
	}

}
