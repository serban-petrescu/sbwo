package spet.sbwo.control.runnable;

import spet.sbwo.config.GeocodingEntry;
import spet.sbwo.control.action.base.IDatabaseAction;
import spet.sbwo.control.action.batch.GeocodeLocationBatch;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.integration.api.geocode.IGeocodingApi;

public class RunGeocodeLocationBatch extends BaseRunnableActionExecutor {
    private final IGeocodingApi api;
    private final int count;

    public RunGeocodeLocationBatch(IDatabaseExecutorCreator database, IGeocodingApi api, GeocodingEntry config) {
        super(database);
        this.api = api;
        this.count = config.getCount();
    }

    @Override
    protected IDatabaseAction<Void, Void> action() {
        return GeocodeLocationBatch.forInput(api, count);
    }

}
