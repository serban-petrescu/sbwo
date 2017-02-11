package spet.sbwo.control.controller.transfer;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.transfer.ImportLocationsAction;
import spet.sbwo.control.channel.LocationImportChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class LocationImportController extends BaseActionExecutor {

	public LocationImportController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public void importLocationParts(List<LocationImportChannel> data) throws ControlException {
		executeAndCommit(new ImportLocationsAction(), data);
	}

}
