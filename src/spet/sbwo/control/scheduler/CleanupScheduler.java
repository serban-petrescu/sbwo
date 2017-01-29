package spet.sbwo.control.scheduler;

import static spet.sbwo.control.util.FileNameUtils.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CleanupScheduler extends BaseScheduler {
	private static final Logger LOG = LoggerFactory.getLogger(CleanupScheduler.class);
	protected final long maxAge;
	protected final List<CleanerBase> cleaners;

	public CleanupScheduler(long delay, long maxAge, List<CleanerBase> cleaners) {
		super(SchedulerType.CLEANUP, 24L * 3600L * 1000L, delay);
		this.maxAge = maxAge;
		this.cleaners = cleaners;
	}

	@Override
	protected long persistedPrevious() {
		return 0;
	}

	@Override
	protected ScheduleInfo build(long when) {
		return new ScheduleInfo(() -> cleaners.forEach(this::clean), when);
	}

	protected void clean(CleanerBase cleaner) {
		File[] files = cleaner.directory.listFiles();
		long minTs = System.currentTimeMillis() - maxAge;
		if (files != null) {
			for (File file : files) {
				long create = cleaner.getCreationTimestamp(file);
				if (create != 0 && create < minTs && !file.delete()) {
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

		protected abstract long getCreationTimestamp(File file);
	}

	protected static class PatternFormattedCleaner extends FormattedCleaner {
		private final Pattern pattern;

		protected PatternFormattedCleaner(File directory, String format, String regex) {
			super(directory, DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault()));
			this.pattern = Pattern.compile(regex);
		}

		@Override
		protected long parse(String name) {
			Matcher matcher = pattern.matcher(name);
			if (matcher.find()) {
				StringBuilder builder = new StringBuilder();
				for (int i = 1; i <= matcher.groupCount(); ++i) {
					builder.append(matcher.group(i));
				}
				String result = builder.toString();
				if (result.isEmpty()) {
					return 0;
				} else {
					return super.parse(result);
				}
			} else {
				return 0;
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
		protected long getCreationTimestamp(File file) {
			try {
				return parse(file.getName());
			} catch (Exception e) {
				LOG.warn("Invalid file exists in cleanup directory: {}.", file.getAbsolutePath(), e);
				return 0;
			}
		}

		protected long parse(String name) {
			return Instant.from(formatter.parse(base(name))).toEpochMilli();
		}
	}

	protected static class PropertyCleaner extends CleanerBase {
		protected PropertyCleaner(File directory) {
			super(directory);
		}

		@Override
		protected long getCreationTimestamp(File file) {
			try {
				return Files.readAttributes(file.toPath(), BasicFileAttributes.class).creationTime().toMillis();
			} catch (IOException e) {
				LOG.warn("Unable to read file properties from cleanup directory: {}.", file.getAbsolutePath(), e);
				return 0;
			}
		}

	}
}
