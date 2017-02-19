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

public class ConfigurationManager {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);
	private static final Gson GSON = new Gson();
	private final File file;

	public ConfigurationManager(String path) {
		this.file = new File(path);
	}

	public Configuration loadData() {
		try (FileInputStream fis = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(fis)) {
			return new Configuration(GSON.fromJson(reader, ConfigurationChannel.class));
		} catch (Exception e) {
			LOG.warn("Unable to open configuration file. Defaulting configuration values.", e);
			return new Configuration();
		}
	}

	public ConfigurationChannel loadChannel() {
		try (FileInputStream fis = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(fis)) {
			return GSON.fromJson(reader, ConfigurationChannel.class);
		} catch (Exception e) {
			LOG.warn("Unable to open configuration file. Defaulting configuration values.", e);
			return new ConfigurationChannel(new Configuration());
		}
	}

	public ConfigurationChannel update(ConfigurationChannel channel) throws ControlException {
		try (FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.defaultCharset().name())) {
			ConfigurationChannel validated = new ConfigurationChannel(new Configuration(channel));
			GSON.toJson(validated, writer);
			return validated;
		} catch (Exception e) {
			LOG.error("Unable to save configuration {} file.", file.getPath(), e);
			throw new ControlException(ControlError.CONFIG_SAVE_ERROR, ConfigurationChannel.class);
		}
	}

}
