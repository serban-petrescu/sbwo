package spet.sbwo.server;

import javax.servlet.Servlet;

import org.eclipse.jetty.servlet.ServletHolder;

public class ServletHandlerBuilder extends ServletHandlerBuilderBase {
	private Servlet servlet;

	ServletHandlerBuilder() {
	}

	public ServletHandlerBuilder setServlet(Servlet servlet) {
		this.servlet = servlet;
		return this;
	}

	public ServletHandlerBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	@Override
	ServletHolder build() {
		ServletHolder result = new ServletHolder(this.servlet);
		result.setInitParameter("cacheControl", "max-age=0,public");
		return result;
	}

}
