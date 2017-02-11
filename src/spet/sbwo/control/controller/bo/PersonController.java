package spet.sbwo.control.controller.bo;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.bo.person.CreatePersonAction;
import spet.sbwo.control.action.bo.person.DeletePersonAction;
import spet.sbwo.control.action.bo.person.ExportPersonAction;
import spet.sbwo.control.action.bo.person.ReadPersonAction;
import spet.sbwo.control.action.bo.person.RestorePersonAction;
import spet.sbwo.control.action.bo.person.UpdatePersonAction;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class PersonController extends BaseActionExecutor {
	protected final int directDeleteInterval;

	public PersonController(IDatabaseExecutorCreator database, int directDeleteInterval) {
		super(database);
		this.directDeleteInterval = directDeleteInterval;
	}

	public int create(PersonChannel data, String username) throws ControlException {
		return executeAndCommit(username, new CreatePersonAction(), data);
	}

	public PersonChannel read(int id) throws ControlException {
		return execute(new ReadPersonAction(), id);
	}

	public void update(int id, PersonChannel data, String username) throws ControlException {
		executeAndCommit(username, new UpdatePersonAction(), data);
	}

	public void delete(int id, String username) throws ControlException {
		executeAndCommit(username, new DeletePersonAction(directDeleteInterval), id);
	}

	public void restore(int id, String username) throws ControlException {
		executeAndCommit(username, new RestorePersonAction(), id);
	}

	public String export(int id) throws ControlException {
		return execute(new ExportPersonAction(), id);
	}
}
