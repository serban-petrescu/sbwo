package spet.sbwo.control.scheduler.model;

import java.time.LocalDateTime;

public class ScheduleInfo {
	private final Runnable task;
	private final LocalDateTime time;

	public ScheduleInfo(Runnable task, LocalDateTime time) {
		this.task = task;
		this.time = time;
	}

	public Runnable getTask() {
		return task;
	}

	public LocalDateTime getTime() {
		return time;
	}

}
