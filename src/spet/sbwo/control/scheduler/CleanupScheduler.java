package spet.sbwo.control.scheduler;

import static spet.sbwo.control.util.FileNameUtils.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CleanupScheduler extends BasePeriodScheduler {
	private static final Logger LOG = LoggerFactory.getLogger(CleanupScheduler.class);
	protected final Period maxAge;
	protected final List<CleanerBase> cleaners;

	public CleanupScheduler(LocalTime time, Period maxAge, List<CleanerBase> cleaners) {
		super(SchedulerType.CLEANUP, time, Period.ofDays(1));
		this.maxAge = maxAge;
		this.cleaners = cleaners;
	}

	@Override
	protected LocalDate persistedPreviousDate() {
		return null;
	}

	@Override
	protected ScheduleInfo build(LocalDateTime when) {
		return new ScheduleInfo(() -> cleaners.forEach(this::clean), when);
	}

	protected void clean(CleanerBase cleaner) {
		File[] files = cleaner.directory.listFiles();
		LocalDateTime now = LocalDateTime.now();
		if (files != null) {
			for (File file : files) {
				LocalDateTime create = cleaner.getCreatedOn(file);
				if (create != null && create.plus(maxAge).isBefore(now) && !file.delete()) {
					LOG.warn("Unable to remove file {}.", file.getAbsolutePath());
				}
			}
		}
	}

	protected abstract static class CleanerBase {
		protected final File directory;

		protected CleanerBase(File directory) {
			this.directory = directory;
		}

		protected abstract LocalDateTime getCreatedOn(File file);
	}

	protected static class PatternFormattedCleaner extends FormattedCleaner {
		private final Pattern pattern;

		protected PatternFormattedCleaner(File directory, String format, String regex) {
			super(directory, DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault()));
			this.pattern = Pattern.compile(regex);
		}

		@Override
		protected LocalDateTime parse(String name) {
			Matcher matcher = pattern.matcher(name);
			if (matcher.find()) {
				StringBuilder builder = new StringBuilder();
				for (int i = 1; i <= matcher.groupCount(); ++i) {
					builder.append(matcher.group(i));
				}
				String result = builder.toString();
				if (result.isEmpty()) {
					return null;
				} else {
					return super.parse(result);
				}
			} else {
				return null;
			}
		}
	}

	protected static class FormattedCleaner extends CleanerBase {
		protected final DateTimeFormatter formatter;

		protected FormattedCleaner(File directory, DateTimeFormatter formatter) {
			super(directory);
			this.formatter = formatter;
		}

		@Override
		protected LocalDateTime getCreatedOn(File file) {
			try {
				return parse(file.getName());
			} catch (Exception e) {
				LOG.warn("Invalid file exists in cleanup directory: {}.", file.getAbsolutePath(), e);
				return null;
			}
		}

		protected LocalDateTime parse(String name) {
			return LocalDateTime.from(formatter.parse(base(name)));
		}
	}

	protected static class PropertyCleaner extends CleanerBase {
		protected PropertyCleaner(File directory) {
			super(directory);
		}

		@Override
		protected LocalDateTime getCreatedOn(File file) {
			try {
				return LocalDateTime.ofInstant(
						Files.readAttributes(file.toPath(), BasicFileAttributes.class).creationTime().toInstant(),
						ZoneId.systemDefault());
			} catch (IOException e) {
				LOG.warn("Unable to read file properties from cleanup directory: {}.", file.getAbsolutePath(), e);
				return null;
			}
		}

	}
}
