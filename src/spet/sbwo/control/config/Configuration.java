package spet.sbwo.control.config;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;

public class Configuration {
	private Duration sessionTimeout;
	private Duration directDeleteInterval;
	private Period databaseBackupInterval;
	private LocalTime databaseBackupStart;
	private File databaseBackupLocation;
	private int schedulerThreads;
	private LocalTime cleanupStart;
	private Period cleanupThreshold;
	private Duration sessionFlushInterval;
	private Duration checkCourtInterval;
	private Duration checkCourtMaxAge;
	private int checkCourtCount;

	public Duration getCheckCourtInterval() {
		return checkCourtInterval;
	}

	public int getCheckCourtCount() {
		return checkCourtCount;
	}

	public Duration getSessionTimeout() {
		return sessionTimeout;
	}

	public Duration getDirectDeleteInterval() {
		return directDeleteInterval;
	}

	public Period getDatabaseBackupInterval() {
		return databaseBackupInterval;
	}

	public LocalTime getDatabaseBackupStart() {
		return databaseBackupStart;
	}

	public File getDatabaseBackupLocation() {
		return databaseBackupLocation;
	}

	public int getSchedulerThreads() {
		return schedulerThreads;
	}

	public LocalTime getCleanupStart() {
		return cleanupStart;
	}

	public Period getCleanupThreshold() {
		return cleanupThreshold;
	}

	public Duration getSessionFlushInterval() {
		return sessionFlushInterval;
	}

	public Duration getCheckCourtMaxAge() {
		return checkCourtMaxAge;
	}

	public static Configuration createDefault() {
		Configuration config = new Configuration();
		config.sessionTimeout = Duration.ofMinutes(60);
		config.directDeleteInterval = Duration.ofMinutes(5);
		config.databaseBackupInterval = Period.ofDays(1);
		config.databaseBackupLocation = new File("backup");
		config.databaseBackupStart = LocalTime.of(9, 0);
		config.schedulerThreads = 2;
		config.cleanupStart = LocalTime.of(10, 0);
		config.cleanupThreshold = Period.ofDays(30);
		config.sessionFlushInterval = Duration.ofMinutes(15);
		config.checkCourtInterval = Duration.ofMinutes(17);
		config.checkCourtMaxAge = Duration.ofDays(1);
		config.checkCourtCount = 5;
		return config;
	}

}
