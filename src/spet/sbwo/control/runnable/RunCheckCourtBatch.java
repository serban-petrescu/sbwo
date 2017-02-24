package spet.sbwo.control.runnable;

import java.time.Duration;

import spet.sbwo.control.action.base.IDatabaseAction;
import spet.sbwo.control.action.bo.expertise.CheckCourtSystemBatch;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.integration.api.court.ICourtSystemApi;

public class RunCheckCourtBatch extends BaseRunnableActionExecutor {
	protected final Duration duration;
	protected final int count;
	protected final ICourtSystemApi api;

	public RunCheckCourtBatch(IDatabaseExecutorCreator database, ICourtSystemApi api, Duration duration, int count) {
		super(database);
		this.duration = duration;
		this.count = count;
		this.api = api;
	}

	@Override
	protected IDatabaseAction<Void, Void> action() {
		return CheckCourtSystemBatch.forInput(duration, count, api);
	}

}
