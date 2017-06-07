package spet.sbwo.control.importer.person;

import spet.sbwo.control.importer.Utils;
import spet.sbwo.control.importer.base.BaseListImporter;
import spet.sbwo.control.importer.base.IEntityProvider;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonBankAccount;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class PersonBankAccountImporter extends BaseListImporter<PersonBankAccount> {
    private IEntityProvider<Person> personProvider;

    public PersonBankAccountImporter(IEntityProvider<Person> personProvider) {
        this.personProvider = personProvider;
    }

    public static List<String> fields() {
        return Arrays.asList("person_number", "account_number", "bank_name", "is_primary");
    }

    @Override
    protected PersonBankAccount build(Map<String, String> entry) {
        PersonBankAccount result = new PersonBankAccount();
        result.setAccountNumber(entry.get("account_number"));
        result.setBank(entry.get("bank_name"));
        result.setPrimary(Utils.toBoolean(entry.get("is_primary")));
        personProvider.getEntity(entry.get("person_number")).getBankAccounts().add(result);
        return result;
    }

}
