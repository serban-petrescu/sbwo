package spet.sbwo.server;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Handler which can serve resource from the classpath.
 *
 * @author Serban Petrescu
 */
public class ResourceHandlerBuilder extends AbstractServletHandlerBuilder {
    private String base;

    ResourceHandlerBuilder() {
        this.cacheEnabled = true;
    }

    /**
     * Sets the base path (package) for files to be served.
     */
    public ResourceHandlerBuilder directory(String path) {
        this.base = path;
        return this;
    }

    /**
     * Enables or disables the cache.
     */
    public ResourceHandlerBuilder cache(boolean enabled) {
        this.cacheEnabled = enabled;
        return this;
    }

    /**
     * Sets the holder's path specification.
     */
    public ResourceHandlerBuilder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    ServletHolder build() {
        ServletHolder holder = new ServletHolder(DefaultServlet.class);
        holder.setInitParameter("resourceBase", ResourceHandlerBuilder.class.getResource(base).toExternalForm());
        holder.setInitParameter("dirAllowed", "true");
        holder.setInitParameter("pathInfoOnly", "true");
        return holder;
    }

}
