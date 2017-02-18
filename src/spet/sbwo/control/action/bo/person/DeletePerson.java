package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.table.Person;

public class DeletePerson extends DeleteEntity<Person> {

	public DeletePerson() {
		super(Person.class, PersonChannel.class);
	}

	public DeletePerson(long directDeleteInterval) {
		super(Person.class, PersonChannel.class, directDeleteInterval);
	}

}
