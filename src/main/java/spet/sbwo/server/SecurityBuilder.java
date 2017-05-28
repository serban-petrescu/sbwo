package spet.sbwo.server;

import java.time.Duration;

import javax.servlet.ServletRequest;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;

import spet.sbwo.control.util.ILoginProvider;

/**
 * Class for enabling basic security features on the server.
 * 
 * @author Serban Petrescu
 */
public class SecurityBuilder {
	private ILoginProvider loginProvider = null;
	private String securedPath;
	private String loginPage;
	private String errorPage;
	private long sessionTimeout = 0;
	private SessionDataStore sessionDataStore;

	/**
	 * Sets the login provider for the server.
	 */
	public SecurityBuilder loginProvider(ILoginProvider loginProvider) {
		this.loginProvider = loginProvider;
		return this;
	}

	/**
	 * Sets the location of the login page.
	 */
	public SecurityBuilder loginPage(String loginPage) {
		this.loginPage = loginPage;
		return this;
	}

	/**
	 * Sets the location of the error page.
	 */
	public SecurityBuilder errorPage(String errorPage) {
		this.errorPage = errorPage;
		return this;
	}

	/**
	 * Sets the path specification which should be protected against
	 * un-authenticated access.
	 */
	public SecurityBuilder securedPath(String securedPath) {
		this.securedPath = securedPath;
		return this;
	}

	/**
	 * Sets the session timeout.
	 */
	public SecurityBuilder sessionTimeout(Duration duration) {
		this.sessionTimeout = duration.getSeconds();
		return this;
	}

	/**
	 * Sets the underlying session data store.
	 */
	public SecurityBuilder sessionDataStore(SessionDataStore sessionDataStore) {
		this.sessionDataStore = sessionDataStore;
		return this;
	}

	ServletContextHandler build(Server server) {
		Constraint constraint = new Constraint();
		constraint.setName(Constraint.__FORM_AUTH);
		constraint.setRoles(new String[] { "user" });
		constraint.setAuthenticate(true);

		ConstraintMapping mapping = new ConstraintMapping();
		mapping.setConstraint(constraint);
		mapping.setPathSpec(this.securedPath);

		ConstraintSecurityHandler security = new ConstraintSecurityHandler();
		CustomFormAuthenticator authenticator = new CustomFormAuthenticator(this.loginPage, this.errorPage, false,
				this.loginProvider);
		authenticator.setAlwaysSaveUri(true);
		security.setAuthenticator(authenticator);
		security.addConstraintMapping(mapping);
		security.setLoginService(new CustomLoginService(this.loginProvider));

		DefaultSessionIdManager manager = new DefaultSessionIdManager(server.getInnerServer());
		SessionHandler session = new SessionHandler();
		session.setSessionIdManager(manager);

		if (sessionDataStore != null) {
			DefaultSessionCache cache = new DefaultSessionCache(session);
			cache.setSessionDataStore(sessionDataStore);
			cache.setRemoveUnloadableSessions(true);
			cache.setSaveOnCreate(false);
			session.setSessionCache(cache);
			session.setMaxInactiveInterval((int) sessionTimeout);
		}

		ServletContextHandler root = new ServletContextHandler(null, "/", true, true);
		root.setSecurityHandler(security);
		root.setSessionHandler(session);
		return root;
	}

	/**
	 * Custom form authenticator which encrypts the user credentials before
	 * passing them to lower layers.
	 */
	private static class CustomFormAuthenticator extends FormAuthenticator {
		private ILoginProvider loginProvider;

		public CustomFormAuthenticator(String login, String error, boolean dispatch, ILoginProvider loginProvider) {
			super(login, error, dispatch);
			this.loginProvider = loginProvider;
		}

		@Override
		public UserIdentity login(String username, Object password, ServletRequest request) {
			String encrypted = this.loginProvider.encryptPassword(username, password.toString());
			if (encrypted != null) {
				return super.login(username, encrypted, request);
			} else {
				return null;
			}
		}

	}

	/**
	 * Adapter class between the control layer login provider and the Jetty
	 * login service.
	 */
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

		@Override
		public boolean validate(UserIdentity user) {
			return user != null && super.validate(user);
		}
	}

	/**
	 * (Temporary) Custom credential class to do password matching.
	 */
	private static class CustomCredential extends Credential {
		private static final long serialVersionUID = 1L;

		private transient ILoginProvider loginProvider;
		private String username;

		public CustomCredential(ILoginProvider loginProvider, String username) {
			this.loginProvider = loginProvider;
			this.username = username;
		}

		@Override
		public boolean check(Object credentials) {
			String password = credentials instanceof char[] ? String.valueOf((char[]) credentials)
					: credentials.toString();
			return this.loginProvider.passwordMatchesEncrypted(this.username, password);
		}

	}
}
