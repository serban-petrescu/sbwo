package spet.sbwo.api.service.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPublic;
import spet.sbwo.control.controller.user.ManagementController;
import spet.sbwo.data.view.UserPlain;

@Path("/user/manage")
public class ManagementService extends BaseService implements IPublic {
	private ManagementController controller;

	public ManagementService(ManagementController controller) {
		this.controller = controller;
	}

	@POST
	@Path("/register")
	@Consumes("application/x-www-form-urlencoded")
	public Response registerUser(@FormParam("username") String username) {
		try {
			controller.registerUser(username);
			return Response.noContent().build();
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@POST
	@Path("/activate")
	@Consumes("application/x-www-form-urlencoded")
	public Response activateUser(@FormParam("username") String username, @FormParam("active") boolean active) {
		try {
			controller.activateUser(username, active);
			return Response.noContent().build();
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@POST
	@Path("/reset")
	@Consumes("application/x-www-form-urlencoded")
	public Response resetUserPassword(@FormParam("username") String username) {
		try {
			controller.resetUserPassword(username);
			return Response.noContent().build();
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@GET
	@Path("/list")
	@Produces("application/json")
	public List<UserPlain> readAllUsers() {
		try {
			return controller.listAllPlains();
		} catch (Exception e) {
			throw mapException(e);
		}
	}

}
