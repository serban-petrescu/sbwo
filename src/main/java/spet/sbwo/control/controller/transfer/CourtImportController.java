package spet.sbwo.control.controller.transfer;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.transfer.ImportCourts;
import spet.sbwo.control.channel.CourtImportChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class CourtImportController extends BaseActionExecutor {

	public CourtImportController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public void importCourts(List<CourtImportChannel> data) throws ControlException {
		executeAndCommit(new ImportCourts(), data);
	}

}
