package spet.sbwo.control.scheduler;

public class ScheduleChannel {
	private final long timestamp;
	private final SchedulerType type;

	protected ScheduleChannel(long timestamp, SchedulerType type) {
		this.timestamp = timestamp;
		this.type = type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public SchedulerType getType() {
		return type;
	}

}