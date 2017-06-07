package spet.sbwo.api.service.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.channel.user.UserHomeTilesChannel;
import spet.sbwo.control.controller.user.TileController;

@Path("/user/tiles")
public class TileService extends BaseService implements IPrivate {
    private final TileController controller;

    public TileService(TileController controller) {
        this.controller = controller;
    }

    @GET
    @Path("/read")
    @Produces("application/json")
    public UserHomeTilesChannel readTiles() {
        try {
            return controller.readTiles(currentUsername());
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @PUT
    @Path("/update")
    @Produces("application/json")
    @Consumes("application/json")
    public UserHomeTilesChannel updateTiles(UserHomeTilesChannel input) {
        try {
            return controller.updateTiles(input, currentUsername());
        } catch (Exception e) {
            throw mapException(e);
        }
    }

}
