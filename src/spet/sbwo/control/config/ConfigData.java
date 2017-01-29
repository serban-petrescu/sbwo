package spet.sbwo.control.config;

import java.io.File;

class ConfigData {
	private final int sessionTimeout;
	private final int directDeleteInterval;
	private final int databaseBackupInterval;
	private final int databaseBackupStart;
	private final File databaseBackupLocation;
	private final int schedulerThreads;
	private final int cleanupStart;
	private final int cleanupThreshold;

	protected ConfigData() {
		this.sessionTimeout = 60;
		this.directDeleteInterval = 5;
		this.databaseBackupInterval = 1;
		this.databaseBackupLocation = new File("backup");
		this.databaseBackupStart = 10 * 60 * 60 * 1000;
		this.schedulerThreads = 2;
		this.cleanupStart = 9 * 60 * 60 * 1000;
		this.cleanupThreshold = 30;
	}

	public ConfigData(ConfigChannel channel) {
		this.sessionTimeout = channel.getSessionTimeout();
		this.directDeleteInterval = channel.getDirectDeleteInterval();
		this.databaseBackupInterval = channel.getDatabaseBackupInterval();
		this.databaseBackupLocation = new File(channel.getDatabaseBackupLocation());
		this.databaseBackupStart = channel.getDatabaseBackupStart();
		this.schedulerThreads = channel.getSchedulerThreads();
		this.cleanupStart = channel.getCleanupStart();
		this.cleanupThreshold = channel.getCleanupThreshold();
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public int getDirectDeleteInterval() {
		return directDeleteInterval;
	}

	public int getDatabaseBackupInterval() {
		return databaseBackupInterval;
	}

	public int getDatabaseBackupStart() {
		return databaseBackupStart;
	}

	public File getDatabaseBackupLocation() {
		return databaseBackupLocation;
	}

	public int getSchedulerThreads() {
		return schedulerThreads;
	}

	public int getCleanupStart() {
		return cleanupStart;
	}

	public int getCleanupThreshold() {
		return cleanupThreshold;
	}

}
