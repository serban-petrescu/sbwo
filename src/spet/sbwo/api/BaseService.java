package spet.sbwo.api;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.data.DatabaseException;

public abstract class BaseService {
	protected static final String X_CSRF_TOKEN_HEADER = "X-CSRF-TOKEN";
	protected static final String X_CSRF_TOKEN_HEADER_FETCH = "Fetch";
	private static ObjectMapper mapper = new ObjectMapper();

	protected void handleException(ControlException e) {
		if (e.getError() == ControlError.ENTITY_NOT_FOUND) {
			throw new NotFoundException();
		} else {
			try {
				String body = mapper.writeValueAsString(this.exceptionToChannel(e));
				throw new InternalServerErrorException(Response.status(500).entity(body).build());
			} catch (JsonProcessingException e1) {
				throw new InternalServerErrorException(e1);
			}
		}
	}

	protected void handleException(Exception e) {
		try {
			String body = mapper.writeValueAsString(this.exceptionToChannel(e));
			throw new InternalServerErrorException(Response.status(500).entity(body).build());
		} catch (JsonProcessingException e1) {
			throw new InternalServerErrorException(e1);
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

	protected String getOrCreateCsrfToken(HttpSession session) {
		String token;
		if (session.getAttribute(X_CSRF_TOKEN_HEADER) != null) {
			token = (String) session.getAttribute(X_CSRF_TOKEN_HEADER);
		} else {
			token = UUID.randomUUID().toString();
			session.setAttribute(X_CSRF_TOKEN_HEADER, token);
		}
		return token;
	}

	protected String getCurrentUsername(HttpServletRequest request) {
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

	protected static class ControlExceptionChannel {
		private String type = "CONTROL";
		private String error;
		private String entity;
		private DatabaseExceptionChannel cause;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getEntity() {
			return entity;
		}

		public void setEntity(String entity) {
			this.entity = entity;
		}

		public DatabaseExceptionChannel getCause() {
			return cause;
		}

		public void setCause(DatabaseExceptionChannel cause) {
			this.cause = cause;
		}

	}

	protected static class DatabaseExceptionChannel {
		private String type = "DATABASE";
		private String error;
		private String details;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}

	}

	protected static class ExceptionChannel {
		private String type = "UNKNOWN";
		private String details;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}

	}
}
