package spet.sbwo.api.service.transfer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.channel.location.LocationImportChannel;
import spet.sbwo.control.controller.transfer.LocationImportController;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Path("/import")
public class LocationImportService extends BaseService implements IPrivate {
    private LocationImportController controller;

    public LocationImportService(LocationImportController controller) {
        this.controller = controller;
    }

    @POST
    @Path("/locations/{separator}/{header}")
    public void importLocationsFromCsv(@PathParam("separator") String separator, @PathParam("header") boolean header,
                                       InputStream body) {
        CSVFormat format = CSVFormat.DEFAULT.withDelimiter(separator.charAt(0)).withSkipHeaderRecord(header);
        try (CSVParser parser = new CSVParser(new InputStreamReader(body), format)) {
            controller.importLocationParts(extractChannels(parser));
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    protected List<LocationImportChannel> extractChannels(CSVParser parser) {
        List<LocationImportChannel> channels = new ArrayList<>();
        for (CSVRecord record : parser) {
            channels.add(new LocationImportChannel(record.get(0), record.get(1), record.get(2), record.get(3),
                record.get(4), record.get(5)));
        }
        return channels;
    }
}
