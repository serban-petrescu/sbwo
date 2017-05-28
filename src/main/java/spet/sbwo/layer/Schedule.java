package spet.sbwo.layer;

import org.picocontainer.MutablePicoContainer;

import spet.sbwo.control.scheduler.duration.CheckCourtSchedulerSetup;
import spet.sbwo.control.scheduler.duration.GeocodeSchedulerSetup;
import spet.sbwo.control.scheduler.duration.SessionCacheSchedulerSetup;
import spet.sbwo.control.scheduler.manager.ScheduleManager;
import spet.sbwo.control.scheduler.period.BackupScheduler;
import spet.sbwo.control.scheduler.period.CleanupScheduler;

public class Schedule {

    private Schedule() {
        super();
    }

    public static void install(MutablePicoContainer container) {
        container.addComponent(CleanupScheduler.class);
        container.addComponent(BackupScheduler.class);
        container.addComponent(CheckCourtSchedulerSetup.class);
        container.addComponent(GeocodeSchedulerSetup.class);
        container.addComponent(SessionCacheSchedulerSetup.class);
        container.addComponent(ScheduleManager.class);
    }

}
