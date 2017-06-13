package spet.sbwo.server;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Abstract base class for all handlers which rely on servlets.
 *
 * @author Serban Petrescu
 */
abstract class AbstractServletHandlerBuilder implements IServletHandlerBuilder {
    protected String path;
    protected boolean cacheEnabled;

    AbstractServletHandlerBuilder() {
        super();
    }

    /**
     * Builds the holder for the servlet.
     */
    abstract ServletHolder build();

    @Override
    public void build(ServletContextHandler root) {
        ServletHolder holder = this.build();
        if (!cacheEnabled) {
            holder.setInitParameter("cacheControl", "max-age=0,public");
        }
        root.addServlet(holder, this.path);
    }
}
