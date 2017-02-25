package spet.sbwo.config;

import java.time.Duration;

public class GeocodingEntry {
	protected Duration interval;
	protected int count;
	protected String key;

	public GeocodingEntry() {
		super();
	}

	public GeocodingEntry(Duration interval, int count, String key) {
		this.interval = interval;
		this.count = count;
		this.key = key;
	}

	public Duration getInterval() {
		return interval;
	}

	public int getCount() {
		return count;
	}

	public String getKey() {
		return key;
	}

}
