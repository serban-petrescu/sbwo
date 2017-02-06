package spet.sbwo.layer;

import spet.sbwo.api.odata.ODataFactory;
import spet.sbwo.server.IServer;
import spet.sbwo.server.ServerBuilder;
import spet.sbwo.server.SessionDataStore;

public class Server {
	private static final String LOGIN_PATH = "/public/login/index.html";

	private final IServer result;

	public Server(Filter filter, Service service, Control control) {
		ServerBuilder serverBuilder = new ServerBuilder();
		serverBuilder.setPort(8080);

		// Root redirect
		serverBuilder.createFilterBuilder().setPath("/").setFilter(filter.getAuthConditionalFilter());
		serverBuilder.createFilterBuilder().setPath("/index.html").setFilter(filter.getAuthConditionalFilter());

		// Public area
		serverBuilder.createFilterBuilder().setPath("/public/rest/*").setFilter(filter.getCsrfTokenFilter());
		serverBuilder.createFilterBuilder().setPath("/public/rest/user/manage/*")
				.setFilter(filter.getLocalAddressFilter());
		serverBuilder.createFilterBuilder().setPath("/public/users/*").setFilter(filter.getLocalAddressFilter());
		serverBuilder.createFilterBuilder().setPath(LOGIN_PATH).setFilter(filter.getAjaxDenyFilter());
		serverBuilder.createServiceBuilder().setPath("/public/rest/*").addServices(service.getPublicServices());
		serverBuilder.createFileBuilder().setPath("/public/*").setBaseDirectory("public");

		// Db console
		serverBuilder.createFilterBuilder().setPath("/db/*").setFilter(filter.getLocalAddressFilter());
		serverBuilder.createServletBuilder().setPath("/db/*").setServlet(service.getDbWebServlet());

		// Private area
		serverBuilder.createFilterBuilder().setPath("/private/api/*").setFilter(filter.getCsrfTokenFilter());
		serverBuilder.createFilterBuilder().setPath("/public/api/rest/utility/file")
				.setFilter(filter.getLocalAddressFilter());
		serverBuilder.createFileBuilder().setPath("/private/web/*").setBaseDirectory("web");
		serverBuilder.createODataBuilder().setPath("/private/api/odata/*").setFactoryClass(ODataFactory.class);
		serverBuilder.createServiceBuilder().setPath("/private/api/rest/*").addServices(service.getPrivateServices());

		// Server security
		serverBuilder.createSecurityBuilder().setSecuredPath("/private/*").setLoginPage(LOGIN_PATH)
				.setErrorPage("/public/login/index.html#/error").setLoginProvider(control.getLoginController())
				.setSessionDataStore(new SessionDataStore(control.getSessionManager()))
				.setSessionTimeout(control.getConfiguration().getSessionTimeout());

		result = serverBuilder.build();
	}

	public IServer getServer() {
		return result;
	}

}
