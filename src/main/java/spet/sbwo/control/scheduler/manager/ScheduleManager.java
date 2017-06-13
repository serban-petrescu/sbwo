package spet.sbwo.control.scheduler.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spet.sbwo.config.SchedulerEntry;
import spet.sbwo.control.scheduler.IScheduler;
import spet.sbwo.control.scheduler.duration.ISimpleScheduleSetup;
import spet.sbwo.control.scheduler.duration.SimpleDurationScheduler;
import spet.sbwo.control.scheduler.model.ScheduleChannel;
import spet.sbwo.control.scheduler.model.ScheduleInfo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ScheduleManager implements IScheduleManager {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleManager.class);
    protected final ScheduledExecutorService executor;
    protected final List<IScheduler> schedulers;
    protected final Set<Wrapper> tasks;

    public ScheduleManager(SchedulerEntry config, IScheduler[] schedulers, ISimpleScheduleSetup[] simpleSetups) {
        this.executor = Executors.newScheduledThreadPool(config.getThreads());
        List<IScheduler> list = new LinkedList<>();
        Collections.addAll(list, schedulers);
        list.addAll(Arrays.stream(simpleSetups).map(s -> (IScheduler) new SimpleDurationScheduler(s))
            .collect(Collectors.toList()));
        this.schedulers = list;
        this.tasks = Collections.synchronizedSet(Collections.newSetFromMap(new IdentityHashMap<>()));
    }

    @Override
    public void start() {
        for (IScheduler scheduler : schedulers) {
            schedule(scheduler, null);
        }
    }

    @Override
    public List<ScheduleChannel> channels() {
        WrapperConsumer consumer = new WrapperConsumer();
        tasks.forEach(consumer);
        return consumer.getResult();
    }

    protected void schedule(IScheduler scheduler, LocalDateTime previous) {
        ScheduleInfo info = scheduler.next(previous);
        if (info != null) {
            long when = Math.max(Duration.between(LocalDateTime.now(), info.getTime()).toMillis(), 0);
            Wrapper task = new Wrapper(info, scheduler);
            tasks.add(task);
            executor.schedule(task, when, TimeUnit.MILLISECONDS);
        }
    }

    private class WrapperConsumer implements Consumer<Wrapper> {
        private final List<ScheduleChannel> result = new ArrayList<>(tasks.size());

        @Override
        public void accept(Wrapper t) {
            result.add(t.toChannel());
        }

        protected List<ScheduleChannel> getResult() {
            return result;
        }
    }

    private class Wrapper implements Runnable {
        private final ScheduleInfo info;
        private final IScheduler parent;

        public Wrapper(ScheduleInfo info, IScheduler parent) {
            super();
            this.info = info;
            this.parent = parent;
        }

        public ScheduleChannel toChannel() {
            return new ScheduleChannel(info.getTime(), parent.type());
        }

        @Override
        public void run() {
            try {
                info.getTask().run();
            } catch (Exception e) {
                LOG.error("Error while running scheduled task.", e);
            }
            tasks.remove(this);
            schedule(parent, LocalDateTime.now());
        }

    }
}
