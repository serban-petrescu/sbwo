package spet.sbwo.api.service.misc;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.util.FolderScanner;
import spet.sbwo.control.util.FolderScanner.FileInfo;
import spet.sbwo.control.util.FolderScanner.QueryResult;
import spet.sbwo.control.util.FolderScanner.RootResult;

import javax.ws.rs.*;

@Path("/utility/file/explore")
public class FileExploreService extends BaseService implements IPrivate {

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
