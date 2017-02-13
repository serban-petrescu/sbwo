package spet.sbwo.api.service.bo;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.channel.JournalChannel;
import spet.sbwo.control.controller.bo.IBoController;

abstract class BaseBoService<C extends JournalChannel, K extends IBoController<C>> extends BaseService {
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
