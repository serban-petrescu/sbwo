package spet.sbwo.control.scheduler.duration;

import java.time.LocalDateTime;

import spet.sbwo.control.scheduler.model.ScheduleInfo;

public class SimpleDurationScheduler extends BaseDurationScheduler {
    private final ISimpleScheduleSetup setup;

    public SimpleDurationScheduler(ISimpleScheduleSetup setup) {
        super(setup.getType(), setup.getInterval(), LocalDateTime.now(), false);
        this.setup = setup;
    }

    @Override
    protected ScheduleInfo build(LocalDateTime when) {
        return new ScheduleInfo(setup.getRunnable(), when);
    }
}
