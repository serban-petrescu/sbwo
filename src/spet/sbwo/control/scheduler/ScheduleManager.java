package spet.sbwo.control.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleManager implements IScheduleManager {
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleManager.class);
	protected final ScheduledExecutorService executor;
	protected final IScheduler[] schedulers;
	protected final Set<Wrapper> tasks;

	protected ScheduleManager(int threads, IScheduler... schedulers) {
		this.executor = Executors.newScheduledThreadPool(threads);
		this.schedulers = schedulers;
		this.tasks = Collections.synchronizedSet(Collections.newSetFromMap(new IdentityHashMap<>()));
	}

	@Override
	public void start() {
		for (IScheduler scheduler : schedulers) {
			schedule(scheduler, 0);
		}
	}

	@Override
	public List<ScheduleChannel> channels() {
		WrapperConsumer consumer = new WrapperConsumer();
		tasks.forEach(consumer);
		return consumer.getResult();
	}

	protected void schedule(IScheduler scheduler, long previous) {
		ScheduleInfo info = scheduler.next(previous);
		if (info != null) {
			long when = Math.max(info.getTimestamp() - System.currentTimeMillis(), 0);
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
			return new ScheduleChannel(info.getTimestamp(), parent.type());
		}

		@Override
		public void run() {
			try {
				info.getTask().run();
			} catch (Exception e) {
				LOG.error("Error while running scheduled task.", e);
			}
			tasks.remove(this);
			schedule(parent, System.currentTimeMillis());
		}

	}
}
