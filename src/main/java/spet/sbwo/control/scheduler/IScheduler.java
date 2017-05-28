package spet.sbwo.control.scheduler;

import java.time.LocalDateTime;

import spet.sbwo.control.scheduler.model.ScheduleInfo;
import spet.sbwo.control.scheduler.model.SchedulerType;

public interface IScheduler {

    ScheduleInfo next(LocalDateTime previous);

    SchedulerType type();

}
