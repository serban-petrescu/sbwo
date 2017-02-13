package spet.sbwo.control.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;

public class Configuration {
	private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);
	private static final Gson GSON = new Gson();
	private File configFile;
	private ConfigData data;

	public Configuration(String path) {
		this.configFile = new File(path);
		try (FileInputStream fis = new FileInputStream(configFile);
				InputStreamReader reader = new InputStreamReader(fis)) {
			this.data = new ConfigData(GSON.fromJson(reader, ConfigChannel.class));
		} catch (Exception e) {
			LOG.warn("Unable to open configuration file. Defaulting configuration values.", e);
			this.data = new ConfigData();
		}
	}

	public ConfigChannel external() {
		return new ConfigChannel(data);
	}

	public synchronized void internal(ConfigChannel channel) throws ControlException {
		try (FileOutputStream fos = new FileOutputStream(configFile);
				OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.defaultCharset().name())) {
			GSON.toJson(channel, writer);
		} catch (Exception e) {
			LOG.error("Unable to save configuration {} file.", configFile.getPath(), e);
			throw new ControlException(ControlError.CONFIG_SAVE_ERROR, ConfigChannel.class);
		}
		this.data = new ConfigData(channel);
	}

	public int getSessionTimeout() {
		return data.getSessionTimeout();
	}

	public int getDirectDeleteInterval() {
		return data.getDirectDeleteInterval();
	}

	public int getDatabaseBackupInterval() {
		return data.getDatabaseBackupInterval();
	}

	public int getDatabaseBackupStart() {
		return data.getDatabaseBackupStart();
	}

	public File getDatabaseBackupLocation() {
		return data.getDatabaseBackupLocation();
	}

	public int getSchedulerThreads() {
		return data.getSchedulerThreads();
	}

	public int getCleanupStart() {
		return data.getCleanupStart();
	}

	public int getCleanupThreshold() {
		return data.getCleanupThreshold();
	}

}
