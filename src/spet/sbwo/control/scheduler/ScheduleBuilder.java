package spet.sbwo.control.scheduler;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
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

	public SimpleSchedulerBuilder simple() {
		SimpleSchedulerBuilder result = new SimpleSchedulerBuilder();
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

	public static class SimpleSchedulerBuilder extends SchedulerBuilder {
		protected Duration duration;
		protected SchedulerType type = SchedulerType.OTHER;
		protected Supplier<Runnable> supplier;

		public SimpleSchedulerBuilder duration(Duration duration) {
			this.duration = duration;
			return this;
		}

		public SimpleSchedulerBuilder type(SchedulerType type) {
			this.type = type;
			return this;
		}

		public SimpleSchedulerBuilder supplier(Supplier<Runnable> supplier) {
			this.supplier = supplier;
			return this;
		}

		public SimpleSchedulerBuilder runnable(Runnable runnable) {
			this.supplier = () -> runnable;
			return this;
		}

		@Override
		protected IScheduler build() {
			return new SimpleDurationScheduler(type, duration, supplier);
		}
	}

	public static class CleanupSchedulerBuilder extends SchedulerBuilder {
		protected LocalTime time = LocalTime.of(10, 0);
		protected Period maxAge = Period.ofDays(1);
		protected List<CleanupScheduler.CleanerBase> cleaners;

		protected CleanupSchedulerBuilder() {
			super();
			cleaners = new LinkedList<>();
		}

		public CleanupSchedulerBuilder time(LocalTime time) {
			this.time = time;
			return this;
		}

		public CleanupSchedulerBuilder maxAge(Period maxAge) {
			this.maxAge = maxAge;
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
			return new CleanupScheduler(time, maxAge, cleaners);
		}
	}

	public static class BackupSchedulerBuilder extends SchedulerBuilder {
		protected LocalTime time = LocalTime.of(10, 0);
		protected Period period;
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

		public BackupSchedulerBuilder time(LocalTime time) {
			this.time = time;
			return this;
		}

		public BackupSchedulerBuilder period(Period period) {
			this.period = period;
			return this;
		}

		@Override
		protected IScheduler build() {
			return new BackupScheduler(directory, time, period, backuper);
		}
	}
}
