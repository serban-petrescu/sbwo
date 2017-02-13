package spet.sbwo.api.service.misc;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.config.ConfigChannel;
import spet.sbwo.control.config.Configuration;

@Path("/utility/file/config")
public class ConfigurationService extends BaseService {
	private final Configuration configuration;

	public ConfigurationService(Configuration configuration) {
		this.configuration = configuration;
	}

	@GET
	@Path("/read")
	@Produces("application/json")
	public ConfigChannel readConfiguration() {
		try {
			return configuration.external();
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")
	public ConfigChannel updateConfiguration(ConfigChannel data) {
		try {
			configuration.internal(data);
			return configuration.external();
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
