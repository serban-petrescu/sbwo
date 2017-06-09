package spet.sbwo.control.scheduler.duration;

import spet.sbwo.config.CheckCourtEntry;
import spet.sbwo.control.runnable.RunCheckCourtBatch;
import spet.sbwo.control.scheduler.model.SchedulerType;

import java.time.Duration;

public class CheckCourtSchedulerSetup implements ISimpleScheduleSetup {
    private final RunCheckCourtBatch batch;
    private final Duration interval;

    public CheckCourtSchedulerSetup(RunCheckCourtBatch batch, CheckCourtEntry config) {
        this.batch = batch;
        this.interval = config.getInterval();
    }

    @Override
    public Duration getInterval() {
        return interval;
    }

    @Override
    public SchedulerType getType() {
        return SchedulerType.COURT_API_BATCH;
    }

    @Override
    public Runnable getRunnable() {
        return batch;
    }

}
