package spet.sbwo.control.scheduler;

import spet.sbwo.control.scheduler.model.ScheduleInfo;
import spet.sbwo.control.scheduler.model.SchedulerType;

import java.time.LocalDateTime;

public interface IScheduler {

    ScheduleInfo next(LocalDateTime previous);

    SchedulerType type();

}
