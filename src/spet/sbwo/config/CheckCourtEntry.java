package spet.sbwo.config;

import java.time.Duration;

public class CheckCourtEntry {
	protected Duration interval;
	protected Duration maxAge;
	protected int count;

	public CheckCourtEntry() {
		super();
	}

	public CheckCourtEntry(Duration interval, Duration maxAge, int count) {
		this.interval = interval;
		this.maxAge = maxAge;
		this.count = count;
	}

	public Duration getInterval() {
		return interval;
	}

	public Duration getMaxAge() {
		return maxAge;
	}

	public int getCount() {
		return count;
	}

}
