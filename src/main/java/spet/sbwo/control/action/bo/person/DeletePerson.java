package spet.sbwo.control.action.bo.person;

import java.time.Duration;

import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Person;

public class DeletePerson extends DeleteEntity<Person> {

	public DeletePerson() {
		super(Person.class, PersonChannel.class);
	}

	public DeletePerson(Duration directDeleteInterval) {
		super(Person.class, PersonChannel.class, directDeleteInterval);
	}

	@Override
	protected void delete(IDatabaseExecutor executor, Person t)  {
		super.delete(executor, t);
		if (t.getLocation() != null) {
			executor.delete(t.getLocation());
		}
	}

}
