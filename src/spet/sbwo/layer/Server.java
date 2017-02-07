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
		serverBuilder.filter().path("/").filter(filter.getAuthConditionalFilter());
		serverBuilder.filter().path("/index.html").filter(filter.getAuthConditionalFilter());

		// Public area
		serverBuilder.filter().path("/public/rest/*").filter(filter.getCsrfTokenFilter());
		serverBuilder.filter().path("/public/rest/user/manage/*")
				.filter(filter.getLocalAddressFilter());
		serverBuilder.filter().path("/public/users/*").filter(filter.getLocalAddressFilter());
		serverBuilder.filter().path(LOGIN_PATH).filter(filter.getAjaxDenyFilter());
		serverBuilder.service().path("/public/rest/*").addAll(service.getPublicServices());
		serverBuilder.file().path("/public/*").directory("public");

		// Db console
		serverBuilder.filter().path("/db/*").filter(filter.getLocalAddressFilter());
		serverBuilder.servlet().path("/db/*").servlet(service.getDbWebServlet());

		// Private area
		serverBuilder.filter().path("/private/api/*").filter(filter.getCsrfTokenFilter());
		serverBuilder.filter().path("/public/api/rest/utility/file")
				.filter(filter.getLocalAddressFilter());
		serverBuilder.file().path("/private/web/*").directory("web");
		serverBuilder.odata().path("/private/api/odata/*").factory(ODataFactory.class);
		serverBuilder.service().path("/private/api/rest/*").addAll(service.getPrivateServices());

		// Server security
		serverBuilder.security().securedPath("/private/*").loginPage(LOGIN_PATH)
				.errorPage("/public/login/index.html#/error").loginProvider(control.getLoginController())
				.sessionDataStore(new SessionDataStore(control.getSessionManager()))
				.sessionTimeout(control.getConfiguration().getSessionTimeout());

		result = serverBuilder.build();
	}

	public IServer getServer() {
		return result;
	}

}
