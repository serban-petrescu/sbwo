package spet.sbwo.api.service.bo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.control.controller.bo.PersonController;

@Path("/person")
public class PersonService extends BaseBoService<PersonChannel, PersonController>implements IPrivate {

	public PersonService(PersonController controller) {
		super(controller);
	}

	@GET
	@Path("/export/{id}")
	@Produces("text/vcard")
	public Response export(@PathParam("id") int id) {
		try {
			return Response.ok().entity(controller.export(id))
					.header("Content-Disposition", "attachment; filename=Contact.vcf").build();
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
