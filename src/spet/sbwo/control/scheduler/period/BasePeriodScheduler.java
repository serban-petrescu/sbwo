package spet.sbwo.control.scheduler.period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import spet.sbwo.control.scheduler.IScheduler;
import spet.sbwo.control.scheduler.model.ScheduleInfo;
import spet.sbwo.control.scheduler.model.SchedulerType;

abstract class BasePeriodScheduler implements IScheduler {
	protected final SchedulerType type;
	protected final LocalTime time;
	protected final Period period;

	public BasePeriodScheduler(SchedulerType type, LocalTime time, Period period) {
		this.type = type;
		this.time = time;
		this.period = period;
	}

	@Override
	public SchedulerType type() {
		return type;
	}

	@Override
	public ScheduleInfo next(LocalDateTime sessionPrevious) {
		LocalDate previousDate = previousDate(sessionPrevious);
		LocalDateTime now = LocalDateTime.now();
		if (previousDate == null) {
			return build(now);
		} else {
			return build(previousDate.plus(period).atTime(time));
		}
	}

	protected LocalDate previousDate(LocalDateTime sessionPrevious) {
		return sessionPrevious == null ? persistedPreviousDate() : sessionPrevious.toLocalDate();
	}

	protected abstract LocalDate persistedPreviousDate();

	protected abstract ScheduleInfo build(LocalDateTime when);
}
