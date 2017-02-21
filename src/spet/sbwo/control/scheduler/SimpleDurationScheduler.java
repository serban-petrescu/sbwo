package spet.sbwo.control.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class SimpleDurationScheduler extends BaseDurationScheduler {
	private final Supplier<Runnable> supplier;

	protected SimpleDurationScheduler(SchedulerType type, Duration duration, Supplier<Runnable> supplier) {
		super(type, duration, LocalDateTime.now(), false);
		this.supplier = supplier;
	}

	@Override
	protected ScheduleInfo build(LocalDateTime when) {
		return new ScheduleInfo(supplier.get(), when);
	}
}
