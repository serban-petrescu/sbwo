package spet.sbwo.control.importer.person;

import spet.sbwo.control.importer.Utils;
import spet.sbwo.control.importer.base.BaseListImporter;
import spet.sbwo.control.importer.base.IEntityProvider;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonTelephone;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class PersonPhoneImporter extends BaseListImporter<PersonTelephone> {
    private IEntityProvider<Person> personProvider;

    public PersonPhoneImporter(IEntityProvider<Person> personProvider) {
        this.personProvider = personProvider;
    }

    public static List<String> fields() {
        return Arrays.asList("person_number", "telephone", "name", "is_primary");
    }

    @Override
    protected PersonTelephone build(Map<String, String> entry) {
        PersonTelephone result = new PersonTelephone();
        result.setTelephone(entry.get("telephone"));
        result.setName(entry.get("name"));
        result.setPrimary(Utils.toBoolean(entry.get("is_primary")));
        personProvider.getEntity(entry.get("person_number")).getTelephones().add(result);
        return result;
    }

}
