package spet.sbwo.api.service.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.controller.user.ManagementController;
import spet.sbwo.data.view.UserPlain;

@Path("/user/manage")
public class ManagementService extends BaseService {
	private ManagementController controller;
	private ObjectWriter jsonWriter;

	public ManagementService(ManagementController controller) {
		this.controller = controller;
		ObjectMapper mapper = new ObjectMapper();
		CollectionType userPlainListType = mapper.getTypeFactory().constructCollectionType(List.class, UserPlain.class);
		this.jsonWriter = new ObjectMapper().writerFor(userPlainListType);
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
	public String readAllUsers() {
		try {
			return jsonWriter.writeValueAsString(controller.listAllPlains());
		} catch (Exception e) {
			throw mapException(e);
		}
	}

}
