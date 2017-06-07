package spet.sbwo.control.action.transfer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.expertise.CourtImportChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Court;

public class ImportCourts extends BaseDatabaseAction<List<CourtImportChannel>, Void> {

    public ImportCourts() {
        super(CourtImportChannel.class);
    }

    @Override
    public Void doRun(List<CourtImportChannel> input, IDatabaseExecutor executor) {
        Map<String, Court> courts = executor.queryList("Court.readAll", Court.class).stream()
            .collect(Collectors.toMap(Court::getCode, Function.identity()));
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
