package spet.sbwo.control.importer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonEmailAddress;

class PersonEmailImporter extends BaseListImporter<PersonEmailAddress> {
	private IEntityProvider<Person> personProvider;

	public PersonEmailImporter(IEntityProvider<Person> personProvider) {
		this.personProvider = personProvider;
	}

	@Override
	protected PersonEmailAddress build(Map<String, String> entry) {
		PersonEmailAddress result = new PersonEmailAddress();
		result.setPerson(personProvider.getEntity(entry.get("person_number")));
		result.setEmail(entry.get("email_address"));
		result.setName(entry.get("name"));
		result.setPrimary(Utils.toBoolean(entry.get("is_primary")));
		return result;
	}

	public static List<String> fields() {
		return Arrays.asList("person_number", "email_address", "name", "is_primary");
	}

}
