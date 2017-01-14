package spet.sbwo.server;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;

public class FileHandlerBuilder extends ServletHandlerBuilderBase {
	private String base;

	FileHandlerBuilder() {
	}

	public FileHandlerBuilder setBaseDirectory(String path) {
		this.base = path;
		return this;
	}

	public FileHandlerBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	@Override
	ServletHolder build() {
		ServletHolder holder = new ServletHolder(DefaultServlet.class);
		holder.setInitParameter("resourceBase", this.base);
		holder.setInitParameter("dirAllowed","true");
		holder.setInitParameter("pathInfoOnly","true");
		return holder;
	}

}
