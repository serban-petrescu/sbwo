package spet.sbwo.api.service.base;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.phase.PhaseInterceptorChain;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.data.DatabaseException;

public abstract class BaseService {
    public static final String X_CSRF_TOKEN_HEADER = "X-CSRF-TOKEN";
    public static final String X_CSRF_TOKEN_HEADER_FETCH = "Fetch";

    protected WebApplicationException mapException(Exception ex) {
        if (isNotFoundException(ex)) {
            return new NotFoundException();
        } else {
            Response entity = Response.status(500).type("application/json").entity(exceptionToEntity(ex)).build();
            return new InternalServerErrorException(entity);
        }
    }

    protected boolean isNotFoundException(Exception ex) {
        return ex instanceof ControlException && ((ControlException) ex).getError() == ControlError.ENTITY_NOT_FOUND;
    }

    protected Object exceptionToEntity(Exception e) {
        if (e instanceof ControlException) {
            return exceptionToChannel((ControlException) e);
        } else if (e instanceof DatabaseException) {
            return exceptionToChannel((DatabaseException) e);
        } else {
            return exceptionToChannel(e);
        }
    }

    protected ControlExceptionChannel exceptionToChannel(ControlException e) {
        ControlExceptionChannel result = new ControlExceptionChannel();
        if (e.getError() != null) {
            result.setError(e.getError().name());
        }
        if (e.getCause() != null) {
            result.setCause(this.exceptionToChannel(e.getCause()));
        }
        if (e.getChannel() != null) {
            result.setEntity(e.getChannel().getSimpleName());
        }
        return result;
    }

    protected DatabaseExceptionChannel exceptionToChannel(DatabaseException e) {
        DatabaseExceptionChannel result = new DatabaseExceptionChannel();
        result.setDetails(e.getDetails());
        result.setError(e.getError().name());
        return result;
    }

    protected ExceptionChannel exceptionToChannel(Exception e) {
        ExceptionChannel result = new ExceptionChannel();
        result.setDetails(e.getMessage());
        return result;
    }

    public static String getOrCreateCsrfToken(HttpSession session) {
        String token;
        if (session.getAttribute(X_CSRF_TOKEN_HEADER) != null) {
            token = (String) session.getAttribute(X_CSRF_TOKEN_HEADER);
        } else {
            token = UUID.randomUUID().toString();
            session.setAttribute(X_CSRF_TOKEN_HEADER, token);
        }
        return token;
    }

    public static String currentUsername() {
        HttpServletRequest request = (HttpServletRequest) PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");
        if (request.getSession(false) != null) {
            if (request.getUserPrincipal() != null) {
                return request.getUserPrincipal().getName();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
