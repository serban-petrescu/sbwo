package spet.sbwo.control.scheduler;

interface IScheduler {

	ScheduleInfo next(long previous);

	SchedulerType type();
	
}
