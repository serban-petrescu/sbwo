package spet.sbwo.control.scheduler;

class ScheduleInfo {
	private Runnable task;
	private long timestamp;

	public ScheduleInfo(Runnable task, long timestamp) {
		super();
		this.task = task;
		this.timestamp = timestamp;
	}

	public Runnable getTask() {
		return task;
	}

	public long getTimestamp() {
		return timestamp;
	}

}
