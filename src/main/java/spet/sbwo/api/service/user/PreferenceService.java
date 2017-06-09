package spet.sbwo.api.service.user;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.api.service.util.JsonpUtils;
import spet.sbwo.control.channel.user.UserPreferenceChannel;
import spet.sbwo.control.controller.user.PreferenceController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/user/preference")
public class PreferenceService extends BaseService implements IPrivate {
    private final PreferenceController controller;

    public PreferenceService(PreferenceController controller) {
        this.controller = controller;
    }

    @GET
    @Path("/read")
    public Response readPreference(@QueryParam("callback") String callback) {
        try {
            return JsonpUtils.response(controller.readPreference(currentUsername()), callback);
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
