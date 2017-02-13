package spet.sbwo.control.controller.bo;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.bo.base.CreateEntityAction;
import spet.sbwo.control.action.bo.base.DeleteEntityAction;
import spet.sbwo.control.action.bo.base.ReadEntityAction;
import spet.sbwo.control.action.bo.base.RestoreEntityAction;
import spet.sbwo.control.action.bo.base.UpdateEntityAction;
import spet.sbwo.control.action.bo.person.CreatePersonAction;
import spet.sbwo.control.action.bo.person.DeletePersonAction;
import spet.sbwo.control.action.bo.person.ExportPersonAction;
import spet.sbwo.control.action.bo.person.ReadPersonAction;
import spet.sbwo.control.action.bo.person.RestorePersonAction;
import spet.sbwo.control.action.bo.person.UpdatePersonAction;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.Person;

public class PersonController extends BaseBoController<Person, PersonChannel> {

	public PersonController(IDatabaseExecutorCreator database, int directDeleteInterval) {
		super(database, directDeleteInterval);
	}

	public String export(int id) throws ControlException {
		return execute(new ExportPersonAction(), id);
	}

	@Override
	protected CreateEntityAction<Person, PersonChannel> createAction() {
		return new CreatePersonAction();
	}

	@Override
	protected ReadEntityAction<Person, PersonChannel> readAction() {
		return new ReadPersonAction();
	}

	@Override
	protected UpdateEntityAction<Person, PersonChannel> updateAction() {
		return new UpdatePersonAction();
	}

	@Override
	protected DeleteEntityAction<Person> deleteAction(int directDeleteInterval) {
		return new DeletePersonAction(directDeleteInterval);
	}

	@Override
	protected RestoreEntityAction<Person> restoreAction() {
		return new RestorePersonAction();
	}
}
