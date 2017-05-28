package spet.sbwo.control.action;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.LoggerFactory;

public enum Texts {
    INSTANCE;

    private final Properties entries;

    private Texts() {
        entries = new Properties();
        try {
            entries.load(Texts.class.getResourceAsStream("texts.properties"));
        } catch (IOException e) {
            LoggerFactory.getLogger(Texts.class).error("Unable to load action error texts.", e);
        }
    }

    public String get(Class<?> clazz) {
        return entries.getProperty(clazz.getName());
    }

}
