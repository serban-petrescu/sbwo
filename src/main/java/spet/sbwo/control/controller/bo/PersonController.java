package spet.sbwo.control.controller.bo;

import spet.sbwo.config.ControlEntry;
import spet.sbwo.control.action.bo.base.*;
import spet.sbwo.control.action.bo.person.*;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.Person;

import java.time.Duration;

public class PersonController extends BaseBoController<Person, PersonChannel> {

    public PersonController(IDatabaseExecutorCreator database, ControlEntry config) {
        super(database, config);
    }

    public String export(int id) {
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
