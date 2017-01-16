package spet.sbwo.server;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;

import spet.sbwo.control.util.ILoginProvider;
import spet.sbwo.control.util.SessionManager;

public class ServerBuilder {
	private int port = 8080;
	private List<IHandlerBuilder> builders;
	private ILoginProvider loginProvider = null;
	private String securedPath;
	private String loginPage;
	private String errorPage;
	private SessionManager sessionManager;
	private int sessionTimeout = 0;

	public ServerBuilder() {
		this.builders = new LinkedList<>();
	}

	public ServerBuilder setPort(int port) {
		this.port = port;
		return this;
	}

	public ServerBuilder setLoginProvider(ILoginProvider loginProvider) {
		this.loginProvider = loginProvider;
		return this;
	}

	public ServerBuilder setLoginPage(String loginPage) {
		this.loginPage = loginPage;
		return this;
	}

	public ServerBuilder setErrorPage(String errorPage) {
		this.errorPage = errorPage;
		return this;
	}

	public ServerBuilder setSecuredPath(String securedPath) {
		this.securedPath = securedPath;
		return this;
	}

	public ServerBuilder setSessionTimeout(int minutes) {
		this.sessionTimeout = minutes * 60;
		return this;
	}

	public ServerBuilder setSessionManager(SessionManager manager) {
		this.sessionManager = manager;
		return this;
	}

	public FileHandlerBuilder createFileBuilder() {
		FileHandlerBuilder builder = new FileHandlerBuilder();
		this.builders.add(builder);
		return builder;
	}

	public ServiceHandlerBuilder createServiceBuilder() {
		ServiceHandlerBuilder builder = new ServiceHandlerBuilder();
		this.builders.add(builder);
		return builder;
	}

	public ServletHandlerBuilder createServletBuilder() {
		ServletHandlerBuilder builder = new ServletHandlerBuilder();
		this.builders.add(builder);
		return builder;
	}

	public ODataHandlerBuilder createODataBuilder() {
		ODataHandlerBuilder builder = new ODataHandlerBuilder();
		this.builders.add(builder);
		return builder;
	}

	public FilterHandlerBuilder createFilterBuilder() {
		FilterHandlerBuilder builder = new FilterHandlerBuilder();
		this.builders.add(builder);
		return builder;
	}

	public IServer build() {
		Server server = new Server(this.port);
		ServletContextHandler root = this.buildRoot(server);

		for (IHandlerBuilder builder : this.builders) {
			builder.build(root);
		}

		server.setHandler(root);

		return server;
	}

	private ServletContextHandler buildRoot(Server server) {
		ServletContextHandler root;
		if (this.loginProvider == null) {
			root = new ServletContextHandler();
		} else {
			Constraint constraint = new Constraint();
			constraint.setName(Constraint.__BASIC_AUTH);
			constraint.setRoles(new String[] { "user" });
			constraint.setAuthenticate(true);

			ConstraintMapping mapping = new ConstraintMapping();
			mapping.setConstraint(constraint);
			mapping.setPathSpec(this.securedPath);

			ConstraintSecurityHandler security = new ConstraintSecurityHandler();
			security.setAuthenticator(new FormAuthenticator(this.loginPage, this.errorPage, false));
			security.addConstraintMapping(mapping);
			security.setLoginService(new CustomLoginService(this.loginProvider));

			DefaultSessionIdManager manager = new DefaultSessionIdManager(server.getInnerServer());
			SessionHandler session = new SessionHandler();
			session.setSessionIdManager(manager);

			if (sessionManager != null) {
				DefaultSessionCache cache = new DefaultSessionCache(session);
				cache.setSessionDataStore(new SessionDataStore(sessionManager));
				cache.setRemoveUnloadableSessions(true);
				cache.setSaveOnCreate(true);
				session.setSessionCache(cache);
				session.setMaxInactiveInterval(sessionTimeout);
			}

			root = new ServletContextHandler(null, "/", true, true);
			root.setSecurityHandler(security);
			root.setSessionHandler(session);
		}
		root.setContextPath("/");
		return root;
	}

	private static class CustomLoginService extends AbstractLoginService {
		private ILoginProvider loginProvider;

		public CustomLoginService(ILoginProvider loginProvider) {
			this.loginProvider = loginProvider;
		}

		@Override
		protected String[] loadRoleInfo(UserPrincipal user) {
			return new String[] { "user" };
		}

		@Override
		protected UserPrincipal loadUserInfo(String username) {
			if (this.loginProvider.userExists(username)) {
				return new UserPrincipal(username, new CustomCredential(this.loginProvider, username));
			} else {
				return null;
			}
		}
	}

	private static class CustomCredential extends Credential {
		private static final long serialVersionUID = 1L;

		private ILoginProvider loginProvider;
		private String username;

		public CustomCredential(ILoginProvider loginProvider, String username) {
			this.loginProvider = loginProvider;
			this.username = username;
		}

		@Override
		public boolean check(Object credentials) {
			String password = credentials instanceof char[] ? String.valueOf((char[]) credentials)
					: credentials.toString();
			return this.loginProvider.passwordMatches(this.username, password);
		}

	}

}
