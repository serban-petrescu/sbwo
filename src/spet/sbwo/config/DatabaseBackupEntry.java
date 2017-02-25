package spet.sbwo.config;

import java.io.File;
import java.time.LocalTime;
import java.time.Period;

public class DatabaseBackupEntry {
	protected Period interval;
	protected LocalTime start;
	protected File location;

	public DatabaseBackupEntry() {
		super();
	}

	public DatabaseBackupEntry(Period interval, LocalTime start, File location) {
		this.interval = interval;
		this.start = start;
		this.location = location;
	}

	public Period getInterval() {
		return interval;
	}

	public LocalTime getStart() {
		return start;
	}

	public File getLocation() {
		return location;
	}

}
