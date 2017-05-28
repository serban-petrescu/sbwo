package spet.sbwo.control.scheduler.duration;

import java.time.Duration;

import spet.sbwo.config.SessionEntry;
import spet.sbwo.control.controller.user.CachedSessionManager;
import spet.sbwo.control.scheduler.model.SchedulerType;

public class SessionCacheSchedulerSetup implements ISimpleScheduleSetup {
    private final Duration flushInterval;
    private final CachedSessionManager manager;

    public SessionCacheSchedulerSetup(SessionEntry config, CachedSessionManager manager) {
        this.flushInterval = config.getFlushInterval();
        this.manager = manager;
    }

    @Override
    public Duration getInterval() {
        return flushInterval;
    }

    @Override
    public SchedulerType getType() {
        return SchedulerType.SESSION_CACHE;
    }

    @Override
    public Runnable getRunnable() {
        return manager::flush;
    }

}
