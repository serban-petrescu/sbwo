package spet.sbwo.server;

import org.eclipse.jetty.servlet.ServletContextHandler;

@FunctionalInterface
public interface IHandlerBuilder {
	void build(ServletContextHandler root);
}
