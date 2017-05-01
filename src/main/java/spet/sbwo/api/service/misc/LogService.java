package spet.sbwo.api.service.misc;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.api.service.util.JsonFragmentStream;
import spet.sbwo.control.util.FolderScanner;
import spet.sbwo.control.util.FolderScanner.FileInfo;
import spet.sbwo.control.util.FolderScanner.QueryResult;

@Path("/utility/file")
public class LogService extends BaseService implements IPrivate {
	private final String logPath;

	public LogService(String logPath) {
		this.logPath = logPath;
	}

	@GET
	@Path("/logs")
	@Produces("application/json")
	public QueryResult<FileInfo> readLogs() {
		try {
			return new FolderScanner(logPath).files("txt");
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
