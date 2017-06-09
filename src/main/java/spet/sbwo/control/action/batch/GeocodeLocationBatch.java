package spet.sbwo.control.action.batch;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.action.base.IDatabaseAction;
import spet.sbwo.control.action.misc.GeocodeLocation;
import spet.sbwo.control.channel.location.LocationChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Location;
import spet.sbwo.integration.api.geocode.IGeocodingApi;

import java.util.List;

public class GeocodeLocationBatch extends BaseDatabaseAction<Integer, Void> {
    private final GeocodeLocation geocodeLocation;

    public GeocodeLocationBatch(IGeocodingApi api) {
        super(LocationChannel.class);
        geocodeLocation = new GeocodeLocation(api);
    }

    public static IDatabaseAction<Void, Void> forInput(IGeocodingApi api, int count) {
        return (i, e) -> new GeocodeLocationBatch(api).run(count, e);
    }

    @Override
    protected Void doRun(Integer input, IDatabaseExecutor executor) {
        List<Location> locations = executor.queryListLimit("Location.getNotGeocoded", Location.class, input);
        for (Location location : locations) {
            geocodeLocation.run(location, executor);
        }
        return null;
    }

}
