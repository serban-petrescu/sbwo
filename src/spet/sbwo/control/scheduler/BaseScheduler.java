package spet.sbwo.control.scheduler;

abstract class BaseScheduler implements IScheduler {
	protected final SchedulerType type;
	protected final long interval;
	protected final long delay;
	
	protected BaseScheduler(SchedulerType type, long interval, long delay) {
		this.type = type;
		this.interval = interval;
		this.delay = delay % interval;
	}
	
	@Override
	public SchedulerType type() {
		return type;
	}
	
	@Override
	public ScheduleInfo next(long sessionPrevious) {
		long candidatePrevious = sessionPrevious == 0 ? persistedPrevious() : sessionPrevious;
		if (isPrevious(candidatePrevious)) {
			return build(getNextTime());
		}
		else {
			return build(System.currentTimeMillis());
		}
	}
	
	protected boolean isPrevious(long candidate) {
		long time = System.currentTimeMillis() - delay;
		long start = time - time % interval + delay;
		return candidate >=  start;
	}
	
	protected long getNextTime() {
		long time = System.currentTimeMillis() - delay ;
		return time - time % interval + interval + delay;
	}
	
	protected abstract long persistedPrevious();
	protected abstract ScheduleInfo build(long when);
}
