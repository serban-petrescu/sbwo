package spet.sbwo.control.controller.transfer;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.transfer.ImportCourts;
import spet.sbwo.control.channel.expertise.CourtImportChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

import java.util.List;

public class CourtImportController extends BaseActionExecutor {

    public CourtImportController(IDatabaseExecutorCreator database) {
        super(database);
    }

    public void importCourts(List<CourtImportChannel> data) {
        executeAndCommit(new ImportCourts(), data);
    }

}
