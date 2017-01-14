package spet.sbwo.server;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

abstract class ServletHandlerBuilderBase implements IHandlerBuilder {
	protected String path;

	ServletHandlerBuilderBase() {
	}

	abstract ServletHolder build();

	@Override
	public void build(ServletContextHandler root) {
		root.addServlet(this.build(), this.path);
	}
}
