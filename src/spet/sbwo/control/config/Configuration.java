package spet.sbwo.control.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;

public class Configuration {
	private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);
	private static final ObjectWriter jsonWriter;
	private static final ObjectReader jsonReader;

	static {
		ObjectMapper mapper = new ObjectMapper();
		jsonWriter = mapper.writerFor(ConfigChannel.class);
		jsonReader = mapper.readerFor(ConfigChannel.class);
	}

	private File configFile;
	private ConfigData data;

	public Configuration(String path) {
		this.configFile = new File(path);
		try (FileInputStream fis = new FileInputStream(configFile)) {
			this.data = new ConfigData(jsonReader.readValue(fis));
		} catch (Exception e) {
			LOG.warn("Unable to open configuration file. Defaulting configuration values.", e);
			this.data = new ConfigData();
		}
	}

	public ConfigChannel external() {
		return new ConfigChannel(data);
	}

	public synchronized void internal(ConfigChannel channel) throws ControlException {
		try (FileOutputStream fos = new FileOutputStream(configFile)) {
			jsonWriter.writeValue(fos, channel);
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
