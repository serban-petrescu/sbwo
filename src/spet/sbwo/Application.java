package spet.sbwo;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import spet.sbwo.control.config.Configuration;
import spet.sbwo.control.config.ConfigurationManager;
import spet.sbwo.layer.Control;
import spet.sbwo.layer.Database;
import spet.sbwo.layer.Integration;
import spet.sbwo.layer.Producer;
import spet.sbwo.layer.Schedule;
import spet.sbwo.layer.Server;
import spet.sbwo.layer.Service;

public class Application {

	private Application() {
		super();
	}

	public static void main(String[] args) {
		MutablePicoContainer container = new PicoBuilder().withCaching().withConstructorInjection()
				.withReflectionLifecycle().build();
		ConfigurationManager manager = new ConfigurationManager("server.json");
		Configuration configuration = manager.loadData();
		container.addComponent(manager);
		container.addComponent(configuration);
		new Database(container);
		new Control(container, configuration);
		new Schedule(container, configuration);
		new Integration(container);
		new Service(container);
		new Producer(container);
		new Server(container);
		container.start();
	}

}
