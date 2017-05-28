package spet.sbwo.config;

import java.time.Duration;

public class SessionEntry {
    protected Duration timeout;
    protected Duration flushInterval;

    public SessionEntry() {
        super();
    }

    public SessionEntry(Duration timeout, Duration flushInterval) {
        this.timeout = timeout;
        this.flushInterval = flushInterval;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public Duration getFlushInterval() {
        return flushInterval;
    }

}
