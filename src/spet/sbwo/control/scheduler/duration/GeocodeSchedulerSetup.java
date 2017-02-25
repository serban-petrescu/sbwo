package spet.sbwo.control.scheduler.duration;

import java.time.Duration;

import spet.sbwo.config.GeocodingEntry;
import spet.sbwo.control.runnable.RunGeocodeLocationBatch;
import spet.sbwo.control.scheduler.model.SchedulerType;

public class GeocodeSchedulerSetup implements ISimpleScheduleSetup {
	private final Duration interval;
	private final RunGeocodeLocationBatch batch;

	public GeocodeSchedulerSetup(GeocodingEntry config, RunGeocodeLocationBatch batch) {
		this.interval = config.getInterval();
		this.batch = batch;
	}

	@Override
	public Duration getInterval() {
		return interval;
	}

	@Override
	public SchedulerType getType() {
		return SchedulerType.GEOCODING_BATCH;
	}

	@Override
	public Runnable getRunnable() {
		return batch;
	}

}
