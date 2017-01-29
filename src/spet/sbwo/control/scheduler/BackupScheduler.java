package spet.sbwo.control.scheduler;

import static spet.sbwo.control.util.FileNameUtils.*;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IBackupCreator;

class BackupScheduler extends BaseScheduler {
	private static final Logger LOG = LoggerFactory.getLogger(BackupScheduler.class);
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("'backup_'yyyyMMdd'_'HHmmss")
			.withZone(ZoneId.systemDefault());
	public static final String FILE_PATTERN = "backup_\\d{8}_\\d{6}";

	protected final File directory;
	protected final IBackupCreator backuper;

	public BackupScheduler(File directory, long interval, long delay, IBackupCreator backuper) {
		super(SchedulerType.BACKUP, interval, delay);
		this.directory = directory;
		if (!this.directory.isDirectory() && !this.directory.mkdirs()) {
			LOG.error("Unable to create database backup directory.");
		}
		this.backuper = backuper;
	}

	@Override
	protected long persistedPrevious() {
		long max = 0;
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				max = Math.max(max, getBackupCandidateTimestamp(file));
			}
		}
		return max;
	}

	protected long getBackupCandidateTimestamp(File file) {
		String name = base(file.getName());
		if (name.matches(FILE_PATTERN)) {
			try {
				return Instant.from(DATE_FORMAT.parse(name)).toEpochMilli();
			} catch (DateTimeParseException e) {
				LOG.warn("Invalid file exists in backup directory: {}.", name);
			}
		}
		return 0;
	}

	@Override
	protected ScheduleInfo build(long when) {
		return new ScheduleInfo(new BackupRunnable(), when);
	}

	protected class BackupRunnable implements Runnable {
		@Override
		public void run() {
			try {
				String name = DATE_FORMAT.format(Instant.now());
				String path = new File(directory, name).getAbsolutePath();
				backuper.createBackup(path);
			} catch (DatabaseException e) {
				LOG.error("Unable to run scheduled database backup.", e);
			}
		}

	}
}
