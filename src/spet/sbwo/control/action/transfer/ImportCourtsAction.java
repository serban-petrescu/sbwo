package spet.sbwo.control.action.transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.CourtImportChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Court;

public class ImportCourtsAction extends BaseDatabaseAction<List<CourtImportChannel>, Void> {

	public ImportCourtsAction() {
		super(CourtImportChannel.class);
	}

	@Override
	public Void doRun(List<CourtImportChannel> input, IDatabaseExecutor executor)
			throws ControlException, DatabaseException {
		Map<String, Court> courts = new HashMap<>();
		for (Court court : executor.select(Court.class).execute()) {
			courts.put(court.getCode(), court);
		}

		for (CourtImportChannel importCourt : input) {
			Court court = courts.get(importCourt.getCode());
			if (court == null) {
				court = new Court();
				court.setCode(importCourt.getCode());
				court.setName(importCourt.getName());
				executor.create(court);
			} else {
				court.setName(importCourt.getName());
			}
		}
		return null;
	}

}
