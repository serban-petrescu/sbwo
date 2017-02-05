package spet.sbwo.api.service.misc;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.api.util.JsonFragmentStream;
import spet.sbwo.control.util.FolderScanner;

@Path("/utility/file")
public class LogService extends BaseService {
	private final ObjectWriter fileWriter;
	private final String logPath;

	public LogService(String logPath) {
		this.logPath = logPath;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TypeFactory factory = mapper.getTypeFactory();
		JavaType fileType = factory.constructParametricType(FolderScanner.QueryResult.class,
				FolderScanner.FileInfo.class);
		this.fileWriter = mapper.writerFor(fileType);
	}

	@GET
	@Path("/logs")
	@Produces("application/json")
	public String readLogs() {
		try {
			return fileWriter.writeValueAsString(new FolderScanner(logPath).files("txt"));
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@GET
	@Path("/log/{name}")
	@Produces("application/json")
	public Response readLog(@PathParam("name") String name) {
		try {
			return Response.ok(new JsonFragmentStream(new File(logPath + File.separator + name + ".txt"))).build();
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}
