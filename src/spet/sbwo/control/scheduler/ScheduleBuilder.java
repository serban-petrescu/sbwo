package spet.sbwo.control.scheduler;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import spet.sbwo.data.access.IBackupCreator;

public class ScheduleBuilder {
	private List<SchedulerBuilder> schedulers;
	private int threads;

	public ScheduleBuilder() {
		this.schedulers = new LinkedList<>();
		this.threads = 1;
	}

	public ScheduleBuilder threads(int threads) {
		this.threads = threads;
		return this;
	}

	public BackupSchedulerBuilder backup() {
		BackupSchedulerBuilder result = new BackupSchedulerBuilder();
		this.schedulers.add(result);
		return result;
	}

	public CleanupSchedulerBuilder cleanup() {
		CleanupSchedulerBuilder result = new CleanupSchedulerBuilder();
		this.schedulers.add(result);
		return result;
	}

	public IScheduleManager build() {
		List<IScheduler> s = schedulers.stream().map(SchedulerBuilder::build).collect(Collectors.toList());
		return new ScheduleManager(threads, s.toArray(new IScheduler[] {}));
	}

	private abstract static class SchedulerBuilder {
		protected abstract IScheduler build();
	}

	public static class CleanupSchedulerBuilder extends SchedulerBuilder {
		protected long delay;
		protected long maxAge;
		protected List<CleanupScheduler.CleanerBase> cleaners;

		protected CleanupSchedulerBuilder() {
			super();
			cleaners = new LinkedList<>();
		}

		public CleanupSchedulerBuilder delayMillis(long delay) {
			this.delay = delay;
			return this;
		}

		public CleanupSchedulerBuilder maxAgeDays(int age) {
			this.maxAge = age * 24L * 3600L * 1000L;
			return this;
		}

		public CleanupSchedulerBuilder addBackupBased(File directory) {
			cleaners.add(new CleanupScheduler.FormattedCleaner(directory, BackupScheduler.DATE_FORMAT));
			return this;
		}

		public CleanupSchedulerBuilder addPatternBased(File directory, String format, String regex) {
			cleaners.add(new CleanupScheduler.PatternFormattedCleaner(directory, format, regex));
			return this;
		}

		public CleanupSchedulerBuilder addFormatBased(File directory, DateTimeFormatter formatter) {
			cleaners.add(new CleanupScheduler.FormattedCleaner(directory, formatter));
			return this;
		}

		public CleanupSchedulerBuilder addPropertyBased(File directory) {
			cleaners.add(new CleanupScheduler.PropertyCleaner(directory));
			return this;
		}

		@Override
		protected IScheduler build() {
			if (delay < 0) {
				throw new IllegalArgumentException();
			}
			return new CleanupScheduler(delay, maxAge, cleaners);
		}
	}

	public static class BackupSchedulerBuilder extends SchedulerBuilder {
		protected long delay;
		protected long interval;
		protected File directory;
		protected IBackupCreator backuper;

		protected BackupSchedulerBuilder() {
			super();
		}

		public BackupSchedulerBuilder directory(File directory) {
			this.directory = directory;
			return this;
		}

		public BackupSchedulerBuilder directory(String directory) {
			this.directory = new File(directory);
			return this;
		}

		public BackupSchedulerBuilder backuper(IBackupCreator backuper) {
			this.backuper = backuper;
			return this;
		}

		public BackupSchedulerBuilder delayMillis(long delay) {
			this.delay = delay;
			return this;
		}

		public BackupSchedulerBuilder intervalDays(int interval) {
			this.interval = interval * 24L * 3600L * 1000L;
			return this;
		}

		@Override
		protected IScheduler build() {
			if (interval <= 0 || delay < 0 || backuper == null || directory == null) {
				throw new IllegalArgumentException();
			}
			return new BackupScheduler(directory, interval, delay, backuper);
		}
	}
}
