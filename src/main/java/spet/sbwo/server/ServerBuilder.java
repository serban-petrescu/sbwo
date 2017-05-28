package spet.sbwo.server;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Builder class for creating servers.
 *
 * @author Serban Petrescu
 */
public class ServerBuilder {
    private int port = 8080;
    private List<IServletHandlerBuilder> builders;
    private SecurityBuilder security;

    public ServerBuilder() {
        this.builders = new LinkedList<>();
    }

    /**
    * Sets the server's port.
    */
    public ServerBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    /**
    * Builds a security builder for the server.
    */
    public SecurityBuilder security() {
        security = new SecurityBuilder();
        return security;
    }

    /**
    * Builds a new file handler builder for the server.
    */
    public FileHandlerBuilder file() {
        FileHandlerBuilder builder = new FileHandlerBuilder();
        this.builders.add(builder);
        return builder;
    }

    /**
    * Builds a new resource handler builder for the server.
    */
    public ResourceHandlerBuilder resource() {
        ResourceHandlerBuilder builder = new ResourceHandlerBuilder();
        this.builders.add(builder);
        return builder;
    }

    /**
    * Builds a new service handler builder for the server.
    */
    public ServiceHandlerBuilder service() {
        ServiceHandlerBuilder builder = new ServiceHandlerBuilder();
        this.builders.add(builder);
        return builder;
    }

    /**
    * Builds a new servlet handler builder for the server.
    */
    public ServletHandlerBuilder servlet() {
        ServletHandlerBuilder builder = new ServletHandlerBuilder();
        this.builders.add(builder);
        return builder;
    }

    /**
    * Builds a new OData service handler builder for the server.
    */
    public ODataHandlerBuilder odata() {
        ODataHandlerBuilder builder = new ODataHandlerBuilder();
        this.builders.add(builder);
        return builder;
    }

    /**
    * Builds a new filter handler builder for the server.
    */
    public FilterHandlerBuilder filter() {
        FilterHandlerBuilder builder = new FilterHandlerBuilder();
        this.builders.add(builder);
        return builder;
    }

    /**
    * Builds the server.
    */
    public IServer build() {
        Server server = new Server(this.port);
        ServletContextHandler root;
        if (security == null) {
            root = new ServletContextHandler();
        } else {
            root = security.build(server);
        }
        root.setContextPath("/");

        for (IServletHandlerBuilder builder : this.builders) {
            builder.build(root);
        }

        server.setHandler(root);
        return server;
    }
}
