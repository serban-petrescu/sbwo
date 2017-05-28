package spet.sbwo.control.runnable;

import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.base.IDatabaseAction;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public abstract class BaseRunnableActionExecutor extends BaseActionExecutor implements Runnable {

    protected BaseRunnableActionExecutor(IDatabaseExecutorCreator database) {
        super(database);
    }

    @Override
    public void run() {
        try {
            executeAndCommit(action(), null);
        } catch (ControlException e) {
            LoggerFactory.getLogger(this.getClass()).error("Suppressed exception during action execution.", e);
        }
    }

    protected abstract IDatabaseAction<Void, Void> action();

}
