package spet.sbwo.layer;

import java.io.File;

import spet.sbwo.control.config.Configuration;
import spet.sbwo.control.scheduler.IScheduleManager;
import spet.sbwo.control.scheduler.ScheduleBuilder;

public class Schedule {
	private final IScheduleManager scheduleManager;

	public Schedule(Database database, Control control) {
		Configuration configuration = control.getConfiguration();
		ScheduleBuilder scheduleBuilder = new ScheduleBuilder().threads(configuration.getSchedulerThreads());
		scheduleBuilder.backup().backuper(database.getFacade()).directory(configuration.getDatabaseBackupLocation())
				.intervalDays(configuration.getDatabaseBackupInterval())
				.delayMillis(configuration.getDatabaseBackupStart());
		scheduleBuilder.cleanup().delayMillis(configuration.getCleanupStart())
				.maxAgeDays(configuration.getCleanupThreshold())
				.addBackupBased(configuration.getDatabaseBackupLocation())
				.addPatternBased(new File("logs"), "yyyyMMdd", "log_(\\d{8})_\\d+");
		scheduleManager = scheduleBuilder.build();
	}

	public IScheduleManager getScheduleManager() {
		return scheduleManager;
	}
}
