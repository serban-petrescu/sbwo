package spet.sbwo.layer;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

import spet.sbwo.config.Configuration;
import spet.sbwo.config.ConfigurationManager;
import spet.sbwo.control.util.serialization.gson.GsonSerializationHelper;
import spet.sbwo.control.util.serialization.ISerializationHelper;

public class Config {

    private Config() {
        super();
    }

    public static void install(MutablePicoContainer container) {
        container.addComponent(ISerializationHelper.class, new GsonSerializationHelper());
        container.addComponent(ConfigurationManager.class, ConfigurationManager.class, new ComponentParameter(),
            new ConstantParameter("server.json"));
        Configuration configuration = container.getComponent(ConfigurationManager.class).read();
        container.addComponent(configuration);
        container.addComponent(configuration.getCheckCourt());
        container.addComponent(configuration.getCleanup());
        container.addComponent(configuration.getControl());
        container.addComponent(configuration.getDatabaseBackup());
        container.addComponent(configuration.getGeocoding());
        container.addComponent(configuration.getScheduler());
        container.addComponent(configuration.getSession());
    }

}
