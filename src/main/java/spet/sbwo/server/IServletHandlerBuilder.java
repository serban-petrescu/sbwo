package spet.sbwo.server;

import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Interface for a servlet handler builder.
 *
 * @author Serban Petrescu
 */
@FunctionalInterface
interface IServletHandlerBuilder {
    void build(ServletContextHandler root);
}
