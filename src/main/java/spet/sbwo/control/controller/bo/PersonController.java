package spet.sbwo.control.controller.bo;

import java.time.Duration;

import spet.sbwo.config.ControlEntry;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.bo.base.CreateEntity;
import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.action.bo.base.ReadEntity;
import spet.sbwo.control.action.bo.base.RestoreEntity;
import spet.sbwo.control.action.bo.base.UpdateEntity;
import spet.sbwo.control.action.bo.person.CreatePerson;
import spet.sbwo.control.action.bo.person.DeletePerson;
import spet.sbwo.control.action.bo.person.ExportPerson;
import spet.sbwo.control.action.bo.person.ReadPerson;
import spet.sbwo.control.action.bo.person.RestorePerson;
import spet.sbwo.control.action.bo.person.UpdatePerson;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.Person;

public class PersonController extends BaseBoController<Person, PersonChannel> {

	public PersonController(IDatabaseExecutorCreator database, ControlEntry config) {
		super(database, config);
	}

	public String export(int id) throws ControlException {
		return execute(new ExportPerson(), id);
	}

	@Override
	protected CreateEntity<Person, PersonChannel> createAction() {
		return new CreatePerson();
	}

	@Override
	protected ReadEntity<Person, PersonChannel> readAction() {
		return new ReadPerson();
	}

	@Override
	protected UpdateEntity<Person, PersonChannel> updateAction() {
		return new UpdatePerson();
	}

	@Override
	protected DeleteEntity<Person> deleteAction(Duration directDeleteInterval) {
		return new DeletePerson(directDeleteInterval);
	}

	@Override
	protected RestoreEntity<Person> restoreAction() {
		return new RestorePerson();
	}
}
