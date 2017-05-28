package spet.sbwo.api.service.misc;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.config.Configuration;
import spet.sbwo.config.ConfigurationManager;

@Path("/utility/file/config")
public class ConfigurationService extends BaseService implements IPrivate {
    private final ConfigurationManager manager;

    public ConfigurationService(ConfigurationManager manager) {
        this.manager = manager;
    }

    @GET
    @Path("/read")
    @Produces("application/json")
    public Configuration readConfiguration() {
        try {
            return manager.read();
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @PUT
    @Path("/update")
    @Produces("application/json")
    @Consumes("application/json")
    public Configuration updateConfiguration(Configuration data) {
        try {
            return manager.update(data);
        } catch (Exception e) {
            throw mapException(e);
        }
    }
}
