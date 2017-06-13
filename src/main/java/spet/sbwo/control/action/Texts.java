package spet.sbwo.control.action;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public enum Texts {
    INSTANCE;

    private final Properties entries;

    Texts() {
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
