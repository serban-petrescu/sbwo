package spet.sbwo.api.service.misc;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.util.FolderScanner;

@Path("/utility/file/explore")
public class FileExploreService extends BaseService {
	private final ObjectWriter fileWriter;
	private final ObjectWriter folderWriter;
	private final ObjectWriter rootsWriter;

	public FileExploreService() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TypeFactory factory = mapper.getTypeFactory();
		JavaType fileType = factory.constructParametricType(FolderScanner.QueryResult.class,
				FolderScanner.FileInfo.class);
		JavaType folderType = factory.constructParametricType(FolderScanner.QueryResult.class, String.class);

		this.fileWriter = mapper.writerFor(fileType);
		this.folderWriter = mapper.writerFor(folderType);
		this.rootsWriter = mapper.writerFor(FolderScanner.RootResult.class);
	}

	@GET
	@Path("/files")
	@Produces("application/json")
	public String exploreFiles(@QueryParam("base") String base,
			@QueryParam("extension") @DefaultValue("") String extension) {
		try {
			return fileWriter.writeValueAsString(new FolderScanner(base).files(extension));
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@GET
	@Path("/folders")
	@Produces("application/json")
	public String exploreFolders(@QueryParam("base") String base) {
		try {
			return folderWriter.writeValueAsString(new FolderScanner(base).folders());
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@GET
	@Path("/roots")
	@Produces("application/json")
	public String exploreRoots() {
		try {
			return rootsWriter.writeValueAsString(FolderScanner.roots());
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}
