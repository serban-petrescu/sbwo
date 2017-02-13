package spet.sbwo.api.service.misc;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.util.FolderScanner;
import spet.sbwo.control.util.FolderScanner.FileInfo;
import spet.sbwo.control.util.FolderScanner.QueryResult;
import spet.sbwo.control.util.FolderScanner.RootResult;

@Path("/utility/file/explore")
public class FileExploreService extends BaseService {

	public FileExploreService() {
		super();
	}

	@GET
	@Path("/files")
	@Produces("application/json")
	public QueryResult<FileInfo> exploreFiles(@QueryParam("base") String base,
			@QueryParam("extension") @DefaultValue("") String extension) {
		try {
			return new FolderScanner(base).files(extension);
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@GET
	@Path("/folders")
	@Produces("application/json")
	public QueryResult<String> exploreFolders(@QueryParam("base") String base) {
		try {
			return new FolderScanner(base).folders();
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@GET
	@Path("/roots")
	@Produces("application/json")
	public RootResult exploreRoots() {
		try {
			return FolderScanner.roots();
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}
