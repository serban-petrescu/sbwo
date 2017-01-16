package spet.sbwo.api;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.control.controller.PersonController;

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
			return this.jsonWriter.writeValueAsString(this.controller.readPerson(id));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@PUT
	@Path("/update/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public String update(@PathParam("id") int id, @Context HttpServletRequest request, InputStream body) {
		try {
			PersonChannel data = this.jsonReader.readValue(body);
			this.controller.updatePerson(id, data, getCurrentUsername(request));
			return this.jsonWriter.writeValueAsString(this.controller.readPerson(id));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@PUT
	@Path("/restore/{id}")
	@Consumes("application/json")
	public String restore(@PathParam("id") int id, @Context HttpServletRequest request) {
		try {
			this.controller.restorePerson(id, getCurrentUsername(request));
			return this.jsonWriter.writeValueAsString(this.controller.readPerson(id));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("text/plain")
	public String create(InputStream body, @Context HttpServletRequest request) {
		try {
			PersonChannel data = this.jsonReader.readValue(body);
			return Integer.toString(this.controller.createPerson(data, getCurrentUsername(request)));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@DELETE
	@Path("/delete/{id}")
	public void delete(@PathParam("id") int id, @Context HttpServletRequest request) {
		try {
			this.controller.deletePerson(id, false, getCurrentUsername(request));
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	@GET
	@Path("/export/{id}")
	@Produces("text/vcard")
	public Response export(@PathParam("id") int id) {
		try {
			return Response.ok().entity(controller.exportPerson(id))
					.header("Content-Disposition", "attachment; filename=Contact.vcf").build();
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}
}
