package spet.sbwo.control.config;

public class ConfigurationChannel {
	private int sessionTimeout;
	private int directDeleteInterval;
	private int databaseBackupInterval;
	private int databaseBackupStart;
	private String databaseBackupLocation;
	private int schedulerThreads;
	private int cleanupStart;
	private int cleanupThreshold;
	private int sessionFlushInterval;

	public ConfigurationChannel() {
		super();
	}

	protected ConfigurationChannel(Configuration data) {
		this.sessionTimeout = data.getSessionTimeout();
		this.directDeleteInterval = data.getDirectDeleteInterval();
		this.databaseBackupInterval = data.getDatabaseBackupInterval();
		this.databaseBackupLocation = data.getDatabaseBackupLocation().getAbsolutePath();
		this.databaseBackupStart = data.getDatabaseBackupStart();
		this.schedulerThreads = data.getSchedulerThreads();
		this.cleanupStart = data.getCleanupStart();
		this.cleanupThreshold = data.getCleanupThreshold();
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public int getDirectDeleteInterval() {
		return directDeleteInterval;
	}

	public void setDirectDeleteInterval(int directDeleteInterval) {
		this.directDeleteInterval = directDeleteInterval;
	}

	public int getDatabaseBackupInterval() {
		return databaseBackupInterval;
	}

	public void setDatabaseBackupInterval(int databaseBackupInterval) {
		this.databaseBackupInterval = databaseBackupInterval;
	}

	public int getDatabaseBackupStart() {
		return databaseBackupStart;
	}

	public void setDatabaseBackupStart(int databaseBackupStart) {
		this.databaseBackupStart = databaseBackupStart;
	}

	public String getDatabaseBackupLocation() {
		return databaseBackupLocation;
	}

	public void setDatabaseBackupLocation(String databaseBackupLocation) {
		this.databaseBackupLocation = databaseBackupLocation;
	}

	public int getSchedulerThreads() {
		return schedulerThreads;
	}

	public void setSchedulerThreads(int schedulerThreads) {
		this.schedulerThreads = schedulerThreads;
	}

	public int getCleanupStart() {
		return cleanupStart;
	}

	public void setCleanupStart(int cleanupStart) {
		this.cleanupStart = cleanupStart;
	}

	public int getCleanupThreshold() {
		return cleanupThreshold;
	}

	public void setCleanupThreshold(int cleanupThreshold) {
		this.cleanupThreshold = cleanupThreshold;
	}

	public int getSessionFlushInterval() {
		return sessionFlushInterval;
	}

	public void setSessionFlushInterval(int sessionFlushInterval) {
		this.sessionFlushInterval = sessionFlushInterval;
	}

}
