package spet.sbwo.control.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.WhereOperator;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.view.DeletedEntity;

public class UtilityController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(UtilityController.class);

	public UtilityController(DatabaseFacade database) {
		super(database);
	}

	public CountChannel readCounts() throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			CountChannel result = new CountChannel();
			result.setDeleted(executor.count(DeletedEntity.class).execute());
			result.setPerson(executor.count(Person.class).where("deleted", WhereOperator.EQ, false).execute());
			return result;
		} catch (DatabaseException e) {
			LOG.warn("Unable to retrieve counts.", e);
			throw new ControlException(e, CountChannel.class);
		}
	}

}
