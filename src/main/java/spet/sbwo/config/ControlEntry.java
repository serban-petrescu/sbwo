package spet.sbwo.config;

import java.time.Duration;

public class ControlEntry {
    protected Duration directDeleteInterval;

    public ControlEntry() {
        super();
    }

    public ControlEntry(Duration directDeleteInterval) {
        this.directDeleteInterval = directDeleteInterval;
    }

    public Duration getDirectDeleteInterval() {
        return directDeleteInterval;
    }

}
