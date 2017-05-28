package spet.sbwo.server;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Handler which can serve files from the file system.
 *
 * @author Serban Petrescu
 */
public class FileHandlerBuilder extends AbstractServletHandlerBuilder {
    private String base;

    FileHandlerBuilder() {
        this.cacheEnabled = true;
    }

    /**
    * Sets the base path for files to be served.
    */
    public FileHandlerBuilder directory(String path) {
        this.base = path;
        return this;
    }

    /**
    * Enables or disables the cache.
    */
    public FileHandlerBuilder cache(boolean enabled) {
        this.cacheEnabled = enabled;
        return this;
    }

    /**
    * Sets the holder's path specification.
    */
    public FileHandlerBuilder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    ServletHolder build() {
        ServletHolder holder = new ServletHolder(DefaultServlet.class);
        holder.setInitParameter("resourceBase", this.base);
        holder.setInitParameter("dirAllowed", "true");
        holder.setInitParameter("pathInfoOnly", "true");
        return holder;
    }

}
