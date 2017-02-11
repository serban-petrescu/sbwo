package spet.sbwo.control.importer;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import spet.sbwo.control.ControlException;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.query.WhereOperator;
import spet.sbwo.data.table.User;

public class DataImportFacade {
	private DatabaseFacade database;

	public DataImportFacade(DatabaseFacade database) {
		this.database = database;
	}

	public void execute(Target target, String username, Map<String, Iterator<Map<String, String>>> data)
			throws ControlException {
		try (IDatabaseExecutor executor = database.createExecutor()) {
			User user = executor.selectSingle(User.class).where("username", WhereOperator.EQ, username).execute();
			switch (target) {
			case PERSON:
				new PersonSuite(data.keySet(), executor).process(data, user).persist();
				break;
			}
			executor.commit();
		} catch (DatabaseException e) {
			throw new ControlException(e);
		}
	}

	public Map<String, List<String>> fields(Target target) {
		switch (target) {
		case PERSON:
			return PersonSuite.fields();
		}
		return Collections.emptyMap();
	}

	public enum Target {
		PERSON
	}
}
