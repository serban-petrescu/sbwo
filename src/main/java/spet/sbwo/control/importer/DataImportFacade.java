package spet.sbwo.control.importer;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.user.ReadByUsernameMandatory;
import spet.sbwo.control.importer.person.PersonSuite;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

public class DataImportFacade {
    private final DatabaseFacade database;

    public DataImportFacade(DatabaseFacade database) {
        this.database = database;
    }

    public void execute(Target target, String username, Map<String, Iterator<Map<String, String>>> data) {
        try (IDatabaseExecutor executor = database.createExecutor()) {
            User user = new ReadByUsernameMandatory().run(username, executor);
            suiteForTarget(target, data.keySet(), executor).ifPresent(suite -> suite.process(data, user).persist());
            executor.commit();
        } catch (DatabaseException e) {
            throw new ControlException(e);
        }
    }

    protected Optional<ISuite> suiteForTarget(Target target, Set<String> inputs, IDatabaseExecutor executor) {
        switch (target) {
            case PERSON:
                return Optional.of(new PersonSuite(inputs, executor));
            default:
                return Optional.empty();
        }
    }

    public Map<String, List<String>> fields(Target target) {
        switch (target) {
            case PERSON:
                return PersonSuite.fields();
            default:
                return Collections.emptyMap();
        }
    }
}
