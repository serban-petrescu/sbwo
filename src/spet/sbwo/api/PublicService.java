package spet.sbwo.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.controller.UserController;

public class PublicService extends BaseService {

	private UserController userController;
	private ObjectWriter jsonWriter;

	public PublicService(UserController userController) {
		super();
		this.userController = userController;
		this.jsonWriter = new ObjectMapper().writer();
	}

	@GET
	@Path("/user/current")
	@Produces("application/json")
	public String readCurrent(@Context HttpServletRequest request) {
		String username = getCurrentUsername(request);
		if (username != null) {
			return "{\"name\": \"" + username + "\"}";
		} else {
			throw new ForbiddenException();
		}
	}

	@HEAD
	@Path("/csrf")
	public Response fetchCsrfToken(@Context HttpServletRequest request) {
		String token = getOrCreateCsrfToken(request.getSession());
		return Response.noContent().header(X_CSRF_TOKEN_HEADER, token).build();
	}

	@POST
	@Path("/user/manage/register")
	@Consumes("application/x-www-form-urlencoded")
	public Response registerUser(@FormParam("username") String username) {
		try {
			this.userController.registerUser(username);
			return Response.noContent().build();
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@POST
	@Path("/user/manage/activate")
	@Consumes("application/x-www-form-urlencoded")
	public Response activateUser(@FormParam("username") String username, @FormParam("active") boolean active) {
		try {
			this.userController.activateUser(username, active);
			return Response.noContent().build();
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@POST
	@Path("/user/manage/reset")
	@Consumes("application/x-www-form-urlencoded")
	public Response resetUserPassword(@FormParam("username") String username) {
		try {
			this.userController.resetUserPassword(username);
			return Response.noContent().build();
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@GET
	@Path("/user/manage/list")
	@Produces("application/json")
	public String readAllUsers() {
		try {
			return this.jsonWriter.writeValueAsString(this.userController.listAllPlains());
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

}
