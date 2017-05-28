package spet.sbwo.api.service.misc;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.api.service.util.JsonpUtils;
import spet.sbwo.config.GeocodingEntry;
import spet.sbwo.integration.api.geocode.IGeocodingApi;
import spet.sbwo.integration.api.geocode.model.Position;

@Path("/utility/geocode")
public class GeocodingService extends BaseService implements IPrivate {
    private final IGeocodingApi api;
    private final String key;

    public GeocodingService(IGeocodingApi api, GeocodingEntry config) {
        this.api = api;
        this.key = config.getKey();
    }

    @GET
    @Path("/key")
    public Response getApiKey(@QueryParam("callback") String callback) {
        try {
            return JsonpUtils.response(key, callback);
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @GET
    @Path("/address")
    @Produces("application/json")
    public Position geocodeAddress(@QueryParam("address") String address) {
        Position result = null;
        try {
            result = api.geocode(address);
        } catch (Exception e) {
            throw mapException(e);
        }
        if (result != null) {
            return result;
        } else {
            throw new NotFoundException();
        }
    }

}
