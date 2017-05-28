package spet.sbwo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.util.ISerializationHelper;

public class ConfigurationManager {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);
    private final File file;
    private final ISerializationHelper helper;

    public ConfigurationManager(ISerializationHelper helper, String path) {
        this.file = new File(path);
        this.helper = helper;
    }

    public Configuration read() {
        try (FileInputStream fis = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(fis)) {
            return helper.deserialize(Configuration.class, reader);
        } catch (Exception e) {
            LOG.warn("Unable to open configuration file. Defaulting configuration values.", e);
            return Configuration.createDefault();
        }
    }

    public Configuration update(Configuration data) {
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.defaultCharset().name())) {
            helper.serialize(data, Configuration.class, writer);
            return data;
        } catch (Exception e) {
            LOG.error("Unable to save configuration {} file.", file.getPath(), e);
            throw new ControlException(ControlError.CONFIG_SAVE_ERROR, Configuration.class);
        }
    }

}
