package spet.sbwo.api.service.user;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.api.service.BaseService;

public class SessionService extends BaseService {
	private static final Logger LOG = LoggerFactory.getLogger(SessionService.class);

	private String loginPath;

	public SessionService(String loginPath) {
		this.loginPath = loginPath;
	}

	@GET
	@Path("/user/current")
	@Produces("application/json")
	public String readCurrent(@Context HttpServletRequest request) {
		String username = getCurrentUsername(request);
		if (username != null) {
			return "{\"username\": \"" + username + "\"}";
		} else {
			throw new ForbiddenException();
		}
	}

	@GET
	@Path("/user/logout")
	public Response logout(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		try {
			return Response.seeOther(new URI(loginPath)).build();
		} catch (URISyntaxException e) {
			LOG.error("Unable to redirect user to logout path.", e);
			return Response.noContent().build();
		}
	}

	@HEAD
	@Path("/csrf")
	public Response fetchCsrfToken(@Context HttpServletRequest request) {
		String token = getOrCreateCsrfToken(request.getSession());
		return Response.noContent().header(X_CSRF_TOKEN_HEADER, token).build();
	}
}
