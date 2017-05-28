package spet.sbwo.control.importer.expertise;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.control.importer.Utils;
import spet.sbwo.control.importer.base.ISuite;
import spet.sbwo.control.importer.misc.LocationImporter;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

import java.util.*;

public class ExpertiseSuite implements ISuite {
    private static final String FINES_FIELD_GROUP = "fines";
    private static final String EXPERTISE_FIELD_GROUP = "expertises";
    private final IDatabaseExecutor executor;
    private ExpertiseFineImporter fineImporter;
    private ExpertiseImporter expertiseImporter;
    private LocationImporter locationImporter;

    public ExpertiseSuite(Set<String> inputs, IDatabaseExecutor executor) {
        this.executor = executor;

        if (inputs.contains(EXPERTISE_FIELD_GROUP)) {
            locationImporter = new LocationImporter(executor);
            expertiseImporter = new ExpertiseImporter(executor, locationImporter);
        } else {
            throw new ControlException(ControlError.IMPORT_FILE_MISSING, ExpertiseChannel.class);
        }

        if (inputs.contains(FINES_FIELD_GROUP)) {
            fineImporter = new ExpertiseFineImporter(expertiseImporter);
        }
    }

    public static Map<String, List<String>> fields() {
        Map<String, List<String>> result = new HashMap<>();
        result.put(EXPERTISE_FIELD_GROUP, ExpertiseImporter.fields());
        result.put(FINES_FIELD_GROUP, ExpertiseFineImporter.fields());
        return result;
    }

    @Override
    public ISuite process(Map<String, Iterator<Map<String, String>>> data, User user) {
        Utils.processImporter(data.get(EXPERTISE_FIELD_GROUP), user, expertiseImporter);
        Utils.processImporter(data.get(FINES_FIELD_GROUP), fineImporter);
        return this;
    }

    @Override
    public ISuite persist() {
        locationImporter.persist(executor);
        expertiseImporter.persist(executor);
        if (fineImporter != null) {
            fineImporter.persist(executor);
        }
        return this;
    }
}
