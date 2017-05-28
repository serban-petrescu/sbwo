package spet.sbwo.control.runnable;

import spet.sbwo.config.CheckCourtEntry;
import spet.sbwo.control.action.base.IDatabaseAction;
import spet.sbwo.control.action.batch.CheckCourtSystemBatch;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.integration.api.court.ICourtSystemApi;

public class RunCheckCourtBatch extends BaseRunnableActionExecutor {
    protected final CheckCourtEntry config;
    protected final ICourtSystemApi api;

    public RunCheckCourtBatch(IDatabaseExecutorCreator database, ICourtSystemApi api, CheckCourtEntry config) {
        super(database);
        this.config = config;
        this.api = api;
    }

    @Override
    protected IDatabaseAction<Void, Void> action() {
        return CheckCourtSystemBatch.forInput(config.getMaxAge(), config.getCount(), api);
    }

}
