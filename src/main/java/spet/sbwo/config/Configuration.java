package spet.sbwo.config;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;

public class Configuration {
    private CheckCourtEntry checkCourt;
    private CleanupEntry cleanup;
    private DatabaseBackupEntry databaseBackup;
    private SchedulerEntry scheduler;
    private SessionEntry session;
    private GeocodingEntry geocoding;
    private ControlEntry control;

    public static Configuration createDefault() {
        Configuration config = new Configuration();
        config.checkCourt = new CheckCourtEntry(Duration.ofMinutes(17), Duration.ofDays(1), 5);
        config.cleanup = new CleanupEntry(LocalTime.of(10, 0), Period.ofDays(30));
        config.control = new ControlEntry(Duration.ofMinutes(5));
        config.databaseBackup = new DatabaseBackupEntry(Period.ofDays(1), LocalTime.of(9, 0), new File("backup"));
        config.scheduler = new SchedulerEntry(3);
        config.session = new SessionEntry(Duration.ofMinutes(60), Duration.ofMinutes(15));
        config.geocoding = new GeocodingEntry(Duration.ofMinutes(14), 10, "");
        return config;
    }

    public CheckCourtEntry getCheckCourt() {
        return checkCourt;
    }

    public CleanupEntry getCleanup() {
        return cleanup;
    }

    public DatabaseBackupEntry getDatabaseBackup() {
        return databaseBackup;
    }

    public SchedulerEntry getScheduler() {
        return scheduler;
    }

    public SessionEntry getSession() {
        return session;
    }

    public GeocodingEntry getGeocoding() {
        return geocoding;
    }

    public ControlEntry getControl() {
        return control;
    }

}
