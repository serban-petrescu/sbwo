package spet.sbwo.server;

import java.util.HashSet;
import java.util.Set;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServiceHandlerBuilder extends ServletHandlerBuilderBase {
	private Set<Object> services;

	ServiceHandlerBuilder() {
		this.services = new HashSet<>();
	}

	public ServiceHandlerBuilder addService(Object service) {
		this.services.add(service);
		return this;
	}

	public ServiceHandlerBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	@Override
	ServletHolder build() {
		ServletHolder result = new ServletHolder(new CXFNonSpringJaxrsServlet(this.services));
		result.setInitParameter("cacheControl", "max-age=0,public");
		return result;
	}

}
