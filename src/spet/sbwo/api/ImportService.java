package spet.sbwo.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.LocationImportChannel;
import spet.sbwo.control.controller.ImportController;
import spet.sbwo.control.importer.DataImportFacade;
import spet.sbwo.control.importer.DataImportFacade.Target;

@Path("/import")
public class ImportService extends BaseService {
	private ImportController controller;
	private DataImportFacade dataImporter;

	public ImportService(ImportController controller, DataImportFacade dataImporter) {
		this.controller = controller;
		this.dataImporter = dataImporter;
	}

	@POST
	@Path("/data/{entity}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public void importData(@PathParam("entity") String entity, @Multipart(value = "files[]") List<Attachment> files,
			@Context HttpServletRequest request) {
		try {
			String username = getCurrentUsername(request);
			switch (entity.toLowerCase()) {
			case "persons":
				importData(Target.PERSON, files, username);
				break;
			}
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	@POST
	@Path("/locations/{separator}/{header}")
	@Consumes({ "text/comma-separated-values", "text/csv", "application/csv", "application/excel",
			"application/vnd.ms-excel", "application/vnd.msexcel" })
	@Produces("application/json")
	public void importLocationsFromCsv(@PathParam("separator") String separator, @PathParam("header") boolean header,
			InputStream body) {
		CSVFormat format = CSVFormat.DEFAULT.withDelimiter(separator.charAt(0)).withSkipHeaderRecord(header);
		try (CSVParser parser = new CSVParser(new InputStreamReader(body), format)) {
			List<LocationImportChannel> channels = new ArrayList<>();
			Iterator<CSVRecord> iterator = parser.iterator();
			while (iterator.hasNext()) {
				CSVRecord record = iterator.next();
				channels.add(new LocationImportChannel(record.get(0), record.get(1), record.get(2), record.get(3),
						record.get(4), record.get(5)));
			}
			this.controller.importLocationParts(channels);
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	protected void importData(DataImportFacade.Target target, List<Attachment> files, String username)
			throws IOException, ControlException {
		Map<String, Iterator<Map<String, String>>> result = new HashMap<>();
		Map<String, List<String>> fields = dataImporter.fields(target);
		List<CSVParser> parsers = new LinkedList<>();
		CSVFormat format = CSVFormat.DEFAULT.withHeader();
		try {
			for (Attachment file : files) {
				String filename = file.getContentDisposition().getFilename().replaceFirst("[.][^.]+$", "");
				if (fields.containsKey(filename)) {
					CSVParser parser = new CSVParser(new InputStreamReader(file.getDataHandler().getInputStream()),
							format);
					result.put(filename, new CsvMapIterator(parser.iterator()));
					parsers.add(parser);
				}
			}
			dataImporter.execute(target, username, result);
		} finally {
			for (CSVParser parser : parsers) {
				parser.close();
			}
		}
	}

	protected static class CsvMapIterator implements Iterator<Map<String, String>> {
		private Iterator<CSVRecord> base;

		public CsvMapIterator(Iterator<CSVRecord> base) {
			this.base = base;
		}

		@Override
		public boolean hasNext() {
			return base.hasNext();
		}

		@Override
		public Map<String, String> next() {
			return base.next().toMap();
		}

	}
}
