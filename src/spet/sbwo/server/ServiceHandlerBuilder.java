package spet.sbwo.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Servlet;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Handler builder class for creating JAX-RS service handlers.
 * 
 * @author Serban Petrescu
 */
public class ServiceHandlerBuilder extends AbstractServletHandlerBuilder {
	private final Set<Object> services;
	private final Set<Class<?>> classes;

	ServiceHandlerBuilder() {
		this.services = new HashSet<>();
		this.classes = new HashSet<>();
		this.cacheEnabled = false;
	}

	/**
	 * Enables or disables the cache.
	 */
	public ServiceHandlerBuilder cache(boolean enabled) {
		this.cacheEnabled = enabled;
		return this;
	}

	/**
	 * Adds a new service instance.
	 */
	public ServiceHandlerBuilder add(Object service) {
		this.services.add(service);
		return this;
	}

	/**
	 * Adds several service instances.
	 */
	public ServiceHandlerBuilder addAll(Object... services) {
		Collections.addAll(this.services, services);
		return this;
	}

	/**
	 * Adds several service instances.
	 */
	public ServiceHandlerBuilder addAll(List<Object> services) {
		this.services.addAll(services);
		return this;
	}

	/**
	 * Adds a new service class.
	 */
	public ServiceHandlerBuilder addClass(Class<?> clazz) {
		this.classes.add(clazz);
		return this;
	}

	/**
	 * Adds several service classes.
	 */
	public ServiceHandlerBuilder addClasses(Class<?>... classes) {
		Collections.addAll(this.classes, classes);
		return this;
	}

	/**
	 * Adds several service classes.
	 */
	public ServiceHandlerBuilder addClasses(List<Class<?>> classes) {
		this.classes.addAll(classes);
		return this;
	}

	/**
	 * Sets the holder's path specification.
	 */
	public ServiceHandlerBuilder path(String path) {
		this.path = path;
		return this;
	}

	@Override
	ServletHolder build() {
		Servlet servlet = new CXFNonSpringJaxrsServlet(new SimpleApplication(services, classes));
		return new ServletHolder(servlet);
	}

	/**
	 * Helper class for exposing the service instances and classes to the JAX-RS
	 * API.
	 */
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
