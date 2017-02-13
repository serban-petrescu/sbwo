package spet.sbwo.api.service.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.controller.user.ManagementController;

@Path("/user")
public class SelfService extends BaseService {
	private final ManagementController managementController;

	public SelfService(ManagementController managementController) {
		this.managementController = managementController;
	}

	@PUT
	@Path("/password/update")
	@Consumes("application/json")
	public void changePassword(PasswordChannel data) {
		try {
			managementController.changeUserPassword(currentUsername(), data.getPassword());
		} catch (Exception e) {
			throw mapException(e);
		}
	}

}

class PasswordChannel {
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
