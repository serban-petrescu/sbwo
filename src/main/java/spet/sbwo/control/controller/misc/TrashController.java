package spet.sbwo.control.controller.misc;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.misc.ForceDeleteList;
import spet.sbwo.control.action.misc.ReadAllTrash;
import spet.sbwo.control.action.misc.RestoreList;
import spet.sbwo.control.channel.misc.TrashChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

import java.util.List;

public class TrashController extends BaseActionExecutor {

    public TrashController(IDatabaseExecutorCreator database) {
        super(database);
    }

    public void delete(List<TrashChannel> entities, String username) {
        executeAndCommit(username, new ForceDeleteList(), entities);
    }

    public void restore(List<TrashChannel> entities, String username) {
        executeAndCommit(username, new RestoreList(), entities);
    }

    public void deleteAll(String username) {
        executeAndCommit(username, new ReadAllTrash().then(new ForceDeleteList()), null);
    }

}
