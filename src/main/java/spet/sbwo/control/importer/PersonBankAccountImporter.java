package spet.sbwo.control.importer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonBankAccount;

class PersonBankAccountImporter extends BaseListImporter<PersonBankAccount> {
	private IEntityProvider<Person> personProvider;

	public PersonBankAccountImporter(IEntityProvider<Person> personProvider) {
		this.personProvider = personProvider;
	}

	@Override
	protected PersonBankAccount build(Map<String, String> entry) {
		PersonBankAccount result = new PersonBankAccount();
		result.setPerson(personProvider.getEntity(entry.get("person_number")));
		result.setAccountNumber(entry.get("account_number"));
		result.setBank(entry.get("bank_name"));
		result.setPrimary(Utils.toBoolean(entry.get("is_primary")));
		return result;
	}

	public static List<String> fields() {
		return Arrays.asList("person_number", "account_number", "bank_name", "is_primary");
	}

}
