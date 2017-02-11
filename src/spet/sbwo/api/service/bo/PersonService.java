package spet.sbwo.api.service.bo;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.control.controller.bo.PersonController;

@Path("/person")
public class PersonService extends BaseService {
	private PersonController controller;
	private ObjectWriter jsonWriter;
	private ObjectReader jsonReader;

	public PersonService(PersonController personController) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.controller = personController;
		this.jsonReader = mapper.readerFor(PersonChannel.class);
		this.jsonWriter = mapper.writerFor(PersonChannel.class);
	}

	@GET
	@Path("/read/{id}")
	@Produces("application/json")
	public String read(@PathParam("id") int id) {
		try {
			return this.jsonWriter.writeValueAsString(this.controller.read(id));
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/update/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public String update(@PathParam("id") int id, InputStream body) {
		try {
			PersonChannel data = this.jsonReader.readValue(body);
			this.controller.update(id, data, currentUsername());
			return this.jsonWriter.writeValueAsString(this.controller.read(id));
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@PUT
	@Path("/restore/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public String restore(@PathParam("id") int id) {
		try {
			this.controller.restore(id, currentUsername());
			return this.jsonWriter.writeValueAsString(this.controller.read(id));
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("text/plain")
	public String create(InputStream body) {
		try {
			PersonChannel data = this.jsonReader.readValue(body);
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
