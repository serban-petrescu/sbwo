package spet.sbwo;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import spet.sbwo.layer.*;

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
