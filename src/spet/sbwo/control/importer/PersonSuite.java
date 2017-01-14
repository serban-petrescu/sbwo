package spet.sbwo.control.importer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.User;

class PersonSuite implements ISuite {
	private static final String BANK_ACCOUNT_FIELD_GROUP = "bankAccounts";
	private static final String PHONE_FIELD_GROUP = "telephones";
	private static final String EMAIL_FIELD_GROUP = "emails";
	private static final String PERSON_FILED_GROUP = "persons";
	private IDatabaseExecutor executor;
	private PersonImporter person;
	private PersonBankAccountImporter bankAccount;
	private PersonEmailImporter email;
	private PersonPhoneImporter phone;
	private LocationImporter location;

	public PersonSuite(Set<String> inputs, IDatabaseExecutor executor) throws ControlException {
		this.executor = executor;

		if (inputs.contains(PERSON_FILED_GROUP)) {
			location = new LocationImporter(executor);
			person = new PersonImporter(location);
		} else {
			throw new ControlException(ControlError.IMPORT_FILE_MISSING, PersonChannel.class);
		}

		if (inputs.contains(BANK_ACCOUNT_FIELD_GROUP)) {
			bankAccount = new PersonBankAccountImporter(person);
		}
		if (inputs.contains(EMAIL_FIELD_GROUP)) {
			email = new PersonEmailImporter(person);
		}
		if (inputs.contains(PHONE_FIELD_GROUP)) {
			phone = new PersonPhoneImporter(person);
		}
	}

	public ISuite process(Map<String, Iterator<Map<String, String>>> data, User user) throws DatabaseException {
		Iterator<Map<String, String>> personData = data.get(PERSON_FILED_GROUP);
		while (personData.hasNext()) {
			Person current = person.process(personData.next());
			current.setCreatedBy(user);
			current.setCreatedOn(new Timestamp(new Date().getTime()));
		}

		if (email != null) {
			Iterator<Map<String, String>> emailData = data.get(EMAIL_FIELD_GROUP);
			while (emailData.hasNext()) {
				email.process(emailData.next());
			}
		}

		if (bankAccount != null) {
			Iterator<Map<String, String>> bankData = data.get(BANK_ACCOUNT_FIELD_GROUP);
			while (bankData.hasNext()) {
				bankAccount.process(bankData.next());
			}
		}

		if (phone != null) {
			Iterator<Map<String, String>> phoneData = data.get(PHONE_FIELD_GROUP);
			while (phoneData.hasNext()) {
				phone.process(phoneData.next());
			}
		}

		return this;
	}

	public ISuite persist() throws DatabaseException {
		location.persist(executor);
		person.persist(executor);
		if (email != null) {
			email.persist(executor);
		}
		if (bankAccount != null) {
			bankAccount.persist(executor);
		}
		if (phone != null) {
			phone.persist(executor);
		}
		return this;
	}

	public static Map<String, List<String>> fields() {
		Map<String, List<String>> result = new HashMap<>();
		result.put(PERSON_FILED_GROUP, PersonImporter.fields());
		result.put(EMAIL_FIELD_GROUP, PersonEmailImporter.fields());
		result.put(PHONE_FIELD_GROUP, PersonPhoneImporter.fields());
		result.put(BANK_ACCOUNT_FIELD_GROUP, PersonBankAccountImporter.fields());
		return result;
	}
}
