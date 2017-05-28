package spet.sbwo.api.service.transfer;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.api.service.util.CsvMapIteratorHolder;
import spet.sbwo.control.importer.DataImportFacade;
import spet.sbwo.control.importer.Target;

@Path("/import")
public class DataImportService extends BaseService implements IPrivate {
	private DataImportFacade dataImporter;

	public DataImportService(DataImportFacade dataImporter) {
		this.dataImporter = dataImporter;
	}

	@POST
	@Path("/data/{entity}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public void importData(@PathParam("entity") String entity, @Multipart(value = "files[]") List<Attachment> files) {
		try {
			String username = currentUsername();
			Target target = getTargetForEntity(entity);
			Map<String, List<String>> fields = dataImporter.fields(target);
			try (CsvMapIteratorHolder hoder = new CsvMapIteratorHolder(fields, files)) {
				dataImporter.execute(target, username, hoder.getResult());
			}
		} catch (Exception e) {
			throw mapException(e);
		}
	}

	protected Target getTargetForEntity(String entity) {
		String lower = entity.toLowerCase();
		if ("persons".equals(lower)) {
			return Target.PERSON;
		}
		return null;
	}
}
