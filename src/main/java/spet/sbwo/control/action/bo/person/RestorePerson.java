package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.RestoreEntity;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.table.Person;

public class RestorePerson extends RestoreEntity<Person> {

	public RestorePerson() {
		super(Person.class, PersonChannel.class);
	}

}
