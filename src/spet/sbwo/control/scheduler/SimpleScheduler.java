package spet.sbwo.control.scheduler;

import java.util.function.Supplier;

public class SimpleScheduler extends BaseScheduler {
	private final Supplier<Runnable> supplier;

	protected SimpleScheduler(SchedulerType type, long interval, long delay, Supplier<Runnable> supplier) {
		super(type, interval, delay);
		this.supplier = supplier;
	}

	@Override
	protected long persistedPrevious() {
		return 0;
	}

	@Override
	protected ScheduleInfo build(long when) {
		return new ScheduleInfo(supplier.get(), when);
	}

}
