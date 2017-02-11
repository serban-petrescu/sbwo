package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.DeleteEntityAction;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.table.Person;

public class DeletePersonAction extends DeleteEntityAction<Person> {

	public DeletePersonAction() {
		super(Person.class, PersonChannel.class);
	}

	public DeletePersonAction(long directDeleteInterval) {
		super(Person.class, PersonChannel.class, directDeleteInterval);
	}

}
