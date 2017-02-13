package spet.sbwo.api.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.api.util.JsonpEntity;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.control.controller.user.PreferenceController;

@Path("/user/preference")
public class PreferenceService extends BaseService {
	private final PreferenceController controller;

	public PreferenceService(PreferenceController controller) {
		this.controller = controller;
	}

	@GET
	@Path("/read")
	public Response readPreference(@QueryParam("callback") String callback) {
		try {
			UserPreferenceChannel preference = controller.readPreference(currentUsername());
			if (callback == null) {
				return Response.ok(preference, "application/json").build();
			} else {
				return Response.ok(new JsonpEntity<>(preference, callback), "application/javascript").build();
			}
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	public UserPreferenceChannel updatePreference(@Context HttpServletRequest request,
			UserPreferenceChannel preference) {
		try {
			return controller.updatePreference(currentUsername(), preference);
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
