package spet.sbwo;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import spet.sbwo.layer.Config;
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
		MutablePicoContainer container = buildContainer();
		Database.install(container);
		Config.install(container);
		Control.install(container);
		Integration.install(container);
		Schedule.install(container);
		Service.install(container);
		Producer.install(container);
		Server.install(container);
		container.start();
	}

	protected static MutablePicoContainer buildContainer() {
		return new PicoBuilder().withCaching().withConstructorInjection().withReflectionLifecycle().build();
	}

}
