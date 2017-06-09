package spet.sbwo.api.service.bo;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.control.channel.base.JournalChannel;
import spet.sbwo.control.controller.bo.IBoController;

import javax.ws.rs.*;

public abstract class BaseBoService<C extends JournalChannel, K extends IBoController<C>> extends BaseService {
    protected final K controller;

    protected BaseBoService(K controller) {
        this.controller = controller;
    }

    @GET
    @Path("/read/{id}")
    @Produces("application/json")
    public C read(@PathParam("id") int id) {
        try {
            return this.controller.read(id);
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public C update(@PathParam("id") int id, C data) {
        try {
            this.controller.update(id, data, currentUsername());
            return this.controller.read(id);
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @PUT
    @Path("/restore/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public C restore(@PathParam("id") int id) {
        try {
            this.controller.restore(id, currentUsername());
            return this.controller.read(id);
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @POST
    @Path("/create")
    @Consumes("application/json")
    @Produces("text/plain")
    public String create(C data) {
        try {
            return Integer.toString(this.controller.create(data, currentUsername()));
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public void delete(@PathParam("id") int id) {
        try {
            this.controller.delete(id, currentUsername());
        } catch (Exception e) {
            throw mapException(e);
        }
    }

}
