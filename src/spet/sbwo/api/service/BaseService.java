package spet.sbwo.api.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.data.DatabaseException;

public abstract class BaseService {
	public static final String X_CSRF_TOKEN_HEADER = "X-CSRF-TOKEN";
	public static final String X_CSRF_TOKEN_HEADER_FETCH = "Fetch";
	protected final ObjectWriter controlExceptionWriter;
	protected final ObjectWriter databaseExceptionWriter;
	protected final ObjectWriter otherExceptionWriter;

	protected BaseService() {
		ObjectMapper mapper = new ObjectMapper();
		controlExceptionWriter = mapper.writerFor(ControlExceptionChannel.class);
		databaseExceptionWriter = mapper.writerFor(DatabaseExceptionChannel.class);
		otherExceptionWriter = mapper.writerFor(ExceptionChannel.class);
	}

	protected WebApplicationException mapException(Exception ex) {
		if (isNotFoundException(ex)) {
			return new NotFoundException();
		} else {
			try {
				String body = exceptionToEntity(ex);
				Response entity = Response.status(500).type("application/json").entity(body).build();
				return new InternalServerErrorException(entity);
			} catch (JsonProcessingException je) {
				return new InternalServerErrorException(je);
			}
		}
	}

	protected boolean isNotFoundException(Exception ex) {
		return ex instanceof ControlException && ((ControlException) ex).getError() == ControlError.ENTITY_NOT_FOUND;
	}

	protected String exceptionToEntity(Exception e) throws JsonProcessingException {
		if (e instanceof ControlException) {
			return controlExceptionWriter.writeValueAsString(exceptionToChannel((ControlException) e));
		} else if (e instanceof DatabaseException) {
			return databaseExceptionWriter.writeValueAsString(exceptionToChannel((DatabaseException) e));
		} else {
			return otherExceptionWriter.writeValueAsString(exceptionToChannel(e));
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

	public static String getCurrentUsername(HttpServletRequest request) {
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
