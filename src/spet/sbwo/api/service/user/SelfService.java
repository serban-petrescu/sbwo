package spet.sbwo.api.service.user;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.controller.user.ManagementController;

@Path("/user")
public class SelfService extends BaseService {
	private final ObjectReader passwordReader;
	private final ManagementController managementController;

	public SelfService(ManagementController managementController) {
		ObjectMapper mapper = new ObjectMapper();
		this.managementController = managementController;
		this.passwordReader = mapper.readerFor(PasswordChannel.class);
	}

	@PUT
	@Path("/password/update")
	@Consumes("application/json")
	public void changePassword(InputStream body) {
		try {
			PasswordChannel data = passwordReader.readValue(body);
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
