package spet.sbwo.server;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.rest.app.AbstractODataApplication;
import org.apache.olingo.odata2.core.rest.app.ODataApplication;
import org.eclipse.jetty.servlet.ServletHolder;

public class ODataHandlerBuilder extends ServletHandlerBuilderBase {
	private Class<? extends ODataServiceFactory> factory;

	ODataHandlerBuilder() {
	}

	public ODataHandlerBuilder setFactoryClass(Class<? extends ODataServiceFactory> factory) {
		this.factory = factory;
		return this;
	}

	public ODataHandlerBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	@Override
	ServletHolder build() {
		ODataApplication application = new CustomODataApplication();
		ServletHolder result = new ServletHolder(new CXFNonSpringJaxrsServlet(application));
		result.setInitParameter("cacheControl", "max-age=0,public");
		return result;
	}

	private class CustomODataApplication extends AbstractODataApplication {
		@Override
		public Class<? extends ODataServiceFactory> getServiceFactoryClass() {
			return factory;
		}

	}
}
