package spet.sbwo.api.service.misc;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.config.ConfigChannel;
import spet.sbwo.control.config.Configuration;

@Path("/utility/file/config")
public class ConfigurationService extends BaseService {
	private final ObjectWriter configWriter;
	private final ObjectReader configReader;
	private final Configuration configuration;

	public ConfigurationService(Configuration configuration) {
		this.configuration = configuration;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		this.configReader = mapper.readerFor(ConfigChannel.class);
		this.configWriter = mapper.writerFor(ConfigChannel.class);
	}

	@GET
	@Path("/read")
	@Produces("application/json")
	public String readConfiguration() {
		try {
			return configWriter.writeValueAsString(configuration.external());
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")
	public String updateConfiguration(InputStream body) {
		try {
			configuration.internal(configReader.readValue(body));
			return configWriter.writeValueAsString(configuration.external());
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
