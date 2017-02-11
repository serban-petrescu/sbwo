package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.RestoreEntityAction;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.table.Person;

public class RestorePersonAction extends RestoreEntityAction<Person> {

	public RestorePersonAction() {
		super(Person.class, PersonChannel.class);
	}

}
