package spet.sbwo.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Servlet;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServiceHandlerBuilder extends ServletHandlerBuilderBase {
	private final Set<Object> services;
	private final Set<Class<?>> classes;

	ServiceHandlerBuilder() {
		this.services = new HashSet<>();
		this.classes = new HashSet<>();
	}

	public ServiceHandlerBuilder addService(Object service) {
		this.services.add(service);
		return this;
	}

	public ServiceHandlerBuilder addServices(Object... services) {
		Collections.addAll(this.services, services);
		return this;
	}

	public ServiceHandlerBuilder addServices(List<Object> services) {
		this.services.addAll(services);
		return this;
	}

	public ServiceHandlerBuilder addClass(Class<?> clazz) {
		this.classes.add(clazz);
		return this;
	}

	public ServiceHandlerBuilder addClasses(Class<?>... classes) {
		Collections.addAll(this.classes, classes);
		return this;
	}

	public ServiceHandlerBuilder addClasses(List<Class<?>> classes) {
		this.classes.addAll(classes);
		return this;
	}

	public ServiceHandlerBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	@Override
	ServletHolder build() {
		Servlet servlet = new CXFNonSpringJaxrsServlet(new SimpleApplication(services, classes));
		ServletHolder result = new ServletHolder(servlet);
		result.setInitParameter("cacheControl", "max-age=0,public");
		return result;
	}

	protected static class SimpleApplication extends Application {
		private final Set<Object> services;
		private final Set<Class<?>> classes;

		protected SimpleApplication(Set<Object> services, Set<Class<?>> classes) {
			this.services = services;
			this.classes = classes;
		}

		@Override
		public Set<Object> getSingletons() {
			return services;
		}

		@Override
		public Set<Class<?>> getClasses() {
			return classes;
		}
	}
}
