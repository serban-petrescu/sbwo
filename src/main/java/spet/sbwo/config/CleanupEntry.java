package spet.sbwo.config;

import java.time.LocalTime;
import java.time.Period;

public class CleanupEntry {
	protected LocalTime start;
	protected Period threshold;

	public CleanupEntry() {
		super();
	}

	public CleanupEntry(LocalTime start, Period threshold) {
		this.start = start;
		this.threshold = threshold;
	}

	public LocalTime getStart() {
		return start;
	}

	public Period getThreshold() {
		return threshold;
	}

}
