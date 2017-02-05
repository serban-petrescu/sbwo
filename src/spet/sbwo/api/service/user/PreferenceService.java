package spet.sbwo.api.service.user;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.control.controller.user.PreferenceController;

@Path("/user/preference")
public class PreferenceService extends BaseService {
	private final PreferenceController controller;
	private final ObjectWriter prefWriter;
	private final ObjectReader prefReader;

	public PreferenceService(PreferenceController controller) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.controller = controller;
		this.prefReader = mapper.readerFor(UserPreferenceChannel.class);
		this.prefWriter = mapper.writerFor(UserPreferenceChannel.class);
	}

	@GET
	@Path("/read")
	public Response readPreference(@Context HttpServletRequest request, @QueryParam("callback") String callback) {
		try {
			UserPreferenceChannel preference = controller.readPreference(getCurrentUsername(request));
			String data = prefWriter.writeValueAsString(preference);
			if (callback == null) {
				return Response.ok(data, "application/json").build();
			} else {
				return Response.ok(callback + "(" + data + ")", "application/javascript").build();
			}
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	public String updatePreference(@Context HttpServletRequest request, InputStream body) {
		try {
			UserPreferenceChannel preference = prefReader.readValue(body);
			UserPreferenceChannel result = controller.updatePreference(getCurrentUsername(request), preference);
			return prefWriter.writeValueAsString(result);
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
