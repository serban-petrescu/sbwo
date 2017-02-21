package spet.sbwo.control.scheduler;

import java.time.LocalDateTime;

interface IScheduler {

	ScheduleInfo next(LocalDateTime previous);

	SchedulerType type();
	
}
