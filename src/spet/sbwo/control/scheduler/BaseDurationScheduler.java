package spet.sbwo.control.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;

abstract class BaseDurationScheduler implements IScheduler {
	protected final SchedulerType type;
	protected final Duration duration;
	protected final LocalDateTime start;
	protected final boolean inclusive;

	public BaseDurationScheduler(SchedulerType type, Duration duration, LocalDateTime start, boolean inclusive) {
		super();
		this.type = type;
		this.duration = duration;
		this.start = start;
		this.inclusive = inclusive;
	}

	@Override
	public SchedulerType type() {
		return type;
	}

	@Override
	public ScheduleInfo next(LocalDateTime sessionPrevious) {
		if (sessionPrevious == null) {
			if (inclusive) {
				return build(start);
			} else {
				return build(start.plus(duration));
			}
		} else {
			return build(sessionPrevious.plus(duration));
		}
	}

	protected abstract ScheduleInfo build(LocalDateTime when);
}
