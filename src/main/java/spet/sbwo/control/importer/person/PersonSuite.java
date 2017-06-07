package spet.sbwo.control.importer.person;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.control.importer.Utils;
import spet.sbwo.control.importer.base.ISuite;
import spet.sbwo.control.importer.misc.LocationImporter;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

import java.util.*;

public class PersonSuite implements ISuite {
    private static final String BANK_ACCOUNT_FIELD_GROUP = "bankAccounts";
    private static final String PHONE_FIELD_GROUP = "telephones";
    private static final String EMAIL_FIELD_GROUP = "emails";
    private static final String PERSON_FIELD_GROUP = "persons";
    private IDatabaseExecutor executor;
    private PersonImporter personImporter;
    private PersonBankAccountImporter bankAccountImporter;
    private PersonEmailImporter emailImporter;
    private PersonPhoneImporter phoneImporter;
    private LocationImporter locationImporter;

    public PersonSuite(Set<String> inputs, IDatabaseExecutor executor) {
        this.executor = executor;

        if (inputs.contains(PERSON_FIELD_GROUP)) {
            locationImporter = new LocationImporter(executor);
            personImporter = new PersonImporter(locationImporter);
        } else {
            throw new ControlException(ControlError.IMPORT_FILE_MISSING, PersonChannel.class);
        }

        if (inputs.contains(BANK_ACCOUNT_FIELD_GROUP)) {
            bankAccountImporter = new PersonBankAccountImporter(personImporter);
        }
        if (inputs.contains(EMAIL_FIELD_GROUP)) {
            emailImporter = new PersonEmailImporter(personImporter);
        }
        if (inputs.contains(PHONE_FIELD_GROUP)) {
            phoneImporter = new PersonPhoneImporter(personImporter);
        }
    }

    public static Map<String, List<String>> fields() {
        Map<String, List<String>> result = new HashMap<>();
        result.put(PERSON_FIELD_GROUP, PersonImporter.fields());
        result.put(EMAIL_FIELD_GROUP, PersonEmailImporter.fields());
        result.put(PHONE_FIELD_GROUP, PersonPhoneImporter.fields());
        result.put(BANK_ACCOUNT_FIELD_GROUP, PersonBankAccountImporter.fields());
        return result;
    }

    public ISuite process(Map<String, Iterator<Map<String, String>>> data, User user) {
        Utils.processImporter(data.get(PERSON_FIELD_GROUP), user, personImporter);
        Utils.processImporter(data.get(EMAIL_FIELD_GROUP), emailImporter);
        Utils.processImporter(data.get(BANK_ACCOUNT_FIELD_GROUP), bankAccountImporter);
        Utils.processImporter(data.get(PHONE_FIELD_GROUP), phoneImporter);
        return this;
    }

    public ISuite persist() {
        personImporter.persist(executor);
        return this;
    }
}
