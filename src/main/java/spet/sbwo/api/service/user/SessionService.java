package spet.sbwo.api.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPublic;
import spet.sbwo.api.service.util.JsonpUtils;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.controller.user.ManagementController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class SessionService extends BaseService implements IPublic {
    private static final Logger LOG = LoggerFactory.getLogger(SessionService.class);

    private final String loginPath;
    private final ManagementController controller;

    public SessionService(ManagementController controller, String loginPath) {
        this.loginPath = loginPath;
        this.controller = controller;
    }

    @GET
    @Path("/user/current")
    public Response readCurrent(@QueryParam("callback") String callback) {
        String username = currentUsername();
        if (username != null) {
            try {
                return JsonpUtils.response(controller.readInfo(username), callback);
            } catch (ControlException e) {
                throw mapException(e);
            }
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
