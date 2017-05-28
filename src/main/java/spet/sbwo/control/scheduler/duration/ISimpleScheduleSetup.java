package spet.sbwo.control.scheduler.duration;

import java.time.Duration;

import spet.sbwo.control.scheduler.model.SchedulerType;

public interface ISimpleScheduleSetup {
    Duration getInterval();

    SchedulerType getType();

    Runnable getRunnable();
}
