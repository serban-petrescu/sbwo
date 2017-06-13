package spet.sbwo.api.service.misc;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.channel.misc.TrashChannel;
import spet.sbwo.control.controller.misc.TrashController;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/trash")
public class TrashService extends BaseService implements IPrivate {
    private TrashController controller;

    public TrashService(TrashController controller) {
        this.controller = controller;
    }

    @DELETE
    @Path("/delete")
    @Consumes("application/json")
    public void delete(List<TrashChannel> data) {
        try {
            this.controller.delete(data, currentUsername());
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @DELETE
    @Path("/delete/all")
    public void deleteAll() {
        try {
            this.controller.deleteAll(currentUsername());
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @PUT
    @Path("/restore")
    @Consumes("application/json")
    public void restore(List<TrashChannel> data) {
        try {
            this.controller.restore(data, currentUsername());
        } catch (Exception e) {
            throw mapException(e);
        }
    }
}
