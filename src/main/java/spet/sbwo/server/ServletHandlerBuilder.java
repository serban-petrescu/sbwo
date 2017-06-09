package spet.sbwo.server;

import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

/**
 * Handler builder for keeping a single servlet instance.
 *
 * @author Serban Petrescu
 */
public class ServletHandlerBuilder extends AbstractServletHandlerBuilder {
    private Servlet servlet;

    ServletHandlerBuilder() {
        this.cacheEnabled = false;
    }

    /**
     * Enables or disables cache.
     */
    public ServletHandlerBuilder cache(boolean enabled) {
        this.cacheEnabled = enabled;
        return this;
    }

    /**
     * Sets the underlying servlet.
     */
    public ServletHandlerBuilder servlet(Servlet servlet) {
        this.servlet = servlet;
        return this;
    }

    /**
     * Sets the holder's path specification.
     */
    public ServletHandlerBuilder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    ServletHolder build() {
        return new ServletHolder(this.servlet);
    }

}
