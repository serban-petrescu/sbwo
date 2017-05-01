package spet.sbwo.control.importer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonTelephone;

class PersonPhoneImporter extends BaseListImporter<PersonTelephone> {
	private IEntityProvider<Person> personProvider;

	public PersonPhoneImporter(IEntityProvider<Person> personProvider) {
		this.personProvider = personProvider;
	}

	@Override
	protected PersonTelephone build(Map<String, String> entry) {
		PersonTelephone result = new PersonTelephone();
		result.setPerson(personProvider.getEntity(entry.get("person_number")));
		result.setTelephone(entry.get("telephone"));
		result.setName(entry.get("name"));
		result.setPrimary(Utils.toBoolean(entry.get("is_primary")));
		return result;
	}

	public static List<String> fields() {
		return Arrays.asList("person_number", "telephone", "name", "is_primary");
	}

}
