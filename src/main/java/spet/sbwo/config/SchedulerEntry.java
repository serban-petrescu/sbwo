package spet.sbwo.config;

public class SchedulerEntry {
    protected int threads;

    public SchedulerEntry() {
        super();
    }

    public SchedulerEntry(int threads) {
        this.threads = threads;
    }

    public int getThreads() {
        return threads;
    }

}
