package spet.sbwo.control.scheduler.period;

import static spet.sbwo.control.util.FileNameUtils.*;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.config.DatabaseBackupEntry;
import spet.sbwo.control.scheduler.model.ScheduleInfo;
import spet.sbwo.control.scheduler.model.SchedulerType;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IBackupCreator;

public class BackupScheduler extends BasePeriodScheduler {
	private static final Logger LOG = LoggerFactory.getLogger(BackupScheduler.class);
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("'backup_'yyyyMMdd'_'HHmmss")
			.withZone(ZoneId.systemDefault());
	public static final String FILE_PATTERN = "backup_\\d{8}_\\d{6}";

	protected final File directory;
	protected final IBackupCreator backuper;

	public BackupScheduler(DatabaseBackupEntry config, IBackupCreator backuper) {
		super(SchedulerType.BACKUP, config.getStart(), config.getInterval());
		this.directory = config.getLocation();
		if (!this.directory.isDirectory() && !this.directory.mkdirs()) {
			LOG.error("Unable to create database backup directory.");
		}
		this.backuper = backuper;
	}

	@Override
	protected LocalDate persistedPreviousDate() {
		LocalDateTime max = null;
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				LocalDateTime candidate = getBackupCandidateTimestamp(file);
				if (max == null || (candidate != null && max.isBefore(candidate))) {
					max = candidate;
				}
			}
		}
		return max == null ? null : max.toLocalDate();
	}

	protected LocalDateTime getBackupCandidateTimestamp(File file) {
		String name = base(file.getName());
		if (name.matches(FILE_PATTERN)) {
			try {
				return LocalDateTime.from(DATE_FORMAT.parse(name));
			} catch (DateTimeParseException e) {
				LOG.warn("Invalid file exists in backup directory: {}.", name);
			}
		}
		return null;
	}

	@Override
	protected ScheduleInfo build(LocalDateTime when) {
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
