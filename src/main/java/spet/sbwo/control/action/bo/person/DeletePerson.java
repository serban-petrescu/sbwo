package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Person;

import java.time.Duration;

public class DeletePerson extends DeleteEntity<Person> {

    public DeletePerson() {
        super(Person.class, PersonChannel.class);
    }

    public DeletePerson(Duration directDeleteInterval) {
        super(Person.class, PersonChannel.class, directDeleteInterval);
    }

    @Override
    protected void delete(IDatabaseExecutor executor, Person t) {
        super.delete(executor, t);
        if (t.getLocation() != null) {
            executor.delete(t.getLocation());
        }
    }

}
