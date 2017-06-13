package spet.sbwo.layer;

import org.h2.server.web.WebServlet;
import org.picocontainer.MutablePicoContainer;
import spet.sbwo.api.filter.AjaxFilter;
import spet.sbwo.api.filter.AuthConditionalFilter;
import spet.sbwo.api.filter.CsrfTokenFilter;
import spet.sbwo.api.filter.LocalAddressFilter;
import spet.sbwo.api.odata.ODataFactory;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.api.service.base.IPublic;
import spet.sbwo.config.Configuration;
import spet.sbwo.control.controller.user.ISessionManager;
import spet.sbwo.control.util.ILoginProvider;
import spet.sbwo.server.IServer;
import spet.sbwo.server.ServerBuilder;
import spet.sbwo.server.SessionDataStore;

public class Server {
    private static final String LOGIN_PATH = "/public/login/index.html";
    private static final String WEB_INDEX_PATH = "/private/web/index.html";

    private Server() {
        super();
    }

    public static void install(MutablePicoContainer container) {
        container.addComponent(Facade.class);
    }

    public static class Facade {
        private final IServer result;
        private final CsrfTokenFilter csrfTokenFilter = new CsrfTokenFilter();
        private final LocalAddressFilter localAddressFilter = new LocalAddressFilter();
        private final AuthConditionalFilter authConditionalFilter = new AuthConditionalFilter(LOGIN_PATH,
            WEB_INDEX_PATH);
        private final AjaxFilter ajaxDenyFilter = new AjaxFilter(false);

        public Facade(IPublic[] publicSingletons, IPrivate[] privateSingletons, WebServlet webServlet,
                      ILoginProvider loginProvider, ISessionManager sessionManager, Configuration configuration) {
            ServerBuilder serverBuilder = new ServerBuilder();
            serverBuilder.setPort(8080);

            // Root redirect
            serverBuilder.filter().path("/").filter(authConditionalFilter);
            serverBuilder.filter().path("/index.html").filter(authConditionalFilter);

            // Public area
            serverBuilder.filter().path("/public/rest/*").filter(csrfTokenFilter);
            serverBuilder.filter().path("/public/rest/user/manage/*").filter(localAddressFilter);
            serverBuilder.filter().path("/public/users/*").filter(localAddressFilter);
            serverBuilder.filter().path(LOGIN_PATH).filter(ajaxDenyFilter);
            serverBuilder.service().path("/public/rest/*").addAll((Object[]) publicSingletons);
            serverBuilder.file().path("/public/*").directory("web/public");
            serverBuilder.file().path("/lib/*").directory("web/lib");

            // Db console
            serverBuilder.filter().path("/db/*").filter(localAddressFilter);
            serverBuilder.servlet().path("/db/*").servlet(webServlet);

            // Private area
            serverBuilder.filter().path("/private/api/*").filter(csrfTokenFilter);
            serverBuilder.file().path("/private/web/*").directory("web/private");
            serverBuilder.odata().path("/private/api/odata/*").factory(ODataFactory.class);
            serverBuilder.service().path("/private/api/rest/*").addAll((Object[]) privateSingletons);

            // Server security
            serverBuilder.security().securedPath("/private/*").loginPage(LOGIN_PATH)
                .errorPage("/public/login/index.html#/error").loginProvider(loginProvider)
                .sessionDataStore(new SessionDataStore(sessionManager))
                .sessionTimeout(configuration.getSession().getTimeout());

            result = serverBuilder.build();
        }

        public void start() {
            result.start();
        }

        public void stop() {
            result.stop();
        }

    }

}
