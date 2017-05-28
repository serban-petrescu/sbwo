package spet.sbwo.control.scheduler.model;

import java.time.LocalDateTime;

public class ScheduleChannel {
    private final LocalDateTime time;
    private final SchedulerType type;

    public ScheduleChannel(LocalDateTime time, SchedulerType type) {
        this.time = time;
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public SchedulerType getType() {
        return type;
    }

}