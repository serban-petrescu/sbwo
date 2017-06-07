package spet.sbwo.control.controller.transfer;

import java.util.List;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.transfer.ImportLocations;
import spet.sbwo.control.channel.location.LocationImportChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class LocationImportController extends BaseActionExecutor {

    public LocationImportController(IDatabaseExecutorCreator database) {
        super(database);
    }

    public void importLocationParts(List<LocationImportChannel> data) {
        executeAndCommit(new ImportLocations(), data);
    }

}
