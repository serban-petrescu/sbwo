package spet.sbwo.server;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.rest.app.AbstractODataApplication;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Builder for an OData service handler.
 * 
 * @author Serban Petrescu
 */
public class ODataHandlerBuilder extends AbstractServletHandlerBuilder {
	private Class<? extends ODataServiceFactory> factory;

	ODataHandlerBuilder() {
		this.cacheEnabled = false;
	}

	/**
	 * Enables or disables the cache.
	 */
	public ODataHandlerBuilder cache(boolean enabled) {
		this.cacheEnabled = enabled;
		return this;
	}

	/**
	 * Sets the OData service factory class.
	 */
	public ODataHandlerBuilder setFactoryClass(Class<? extends ODataServiceFactory> factory) {
		this.factory = factory;
		return this;
	}

	/**
	 * Sets the handler's path specification.
	 */
	public ODataHandlerBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	@Override
	ServletHolder build() {
		return new ServletHolder(new CXFNonSpringJaxrsServlet(new CustomODataApplication()));
	}

	/**
	 * Helper class for passing the class reference to the CXF servlet instead
	 * of the class name.
	 */
	private class CustomODataApplication extends AbstractODataApplication {
		@Override
		public Class<? extends ODataServiceFactory> getServiceFactoryClass() {
			return factory;
		}

	}
}
