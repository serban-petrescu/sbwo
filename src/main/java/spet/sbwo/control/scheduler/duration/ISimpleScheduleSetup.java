package spet.sbwo.control.scheduler.duration;

import spet.sbwo.control.scheduler.model.SchedulerType;

import java.time.Duration;

public interface ISimpleScheduleSetup {
    Duration getInterval();

    SchedulerType getType();

    Runnable getRunnable();
}
