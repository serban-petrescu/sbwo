package spet.sbwo.control.importer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.domain.IdentityCardType;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonJuridical;
import spet.sbwo.data.table.PersonNatural;

class PersonImporter extends BaseMapImporter<Person> {
	private LocationImporter locationImporter;

	public PersonImporter(LocationImporter locationImporter) {
		this.locationImporter = locationImporter;
	}

	@Override
	public Person process(Map<String, String> entry) throws DatabaseException {
		Person result;
		if ("0".equals(entry.get("type"))) {
			PersonNatural natural = new PersonNatural();
			natural.setFirstName(entry.get("natural_first_name"));
			natural.setLastName(entry.get("natural_last_name"));
			natural.setPersonalNumber(entry.get("natural_personal_number"));
			natural.setIdentityCardType(Utils.toEnum(IdentityCardType.class, entry.get("natural_id_card_type")));
			natural.setIdentityCardNumber(entry.get("natural_id_card_number"));
			result = natural;
		} else {
			PersonJuridical juridical = new PersonJuridical();
			juridical.setName(entry.get("juridical_name"));
			juridical.setIdNumber(entry.get("juridical_id_number"));
			juridical.setRegNumber(entry.get("juridical_reg_number"));
			juridical.setJointStock(Utils.toDecimal(entry.get("juridical_joint_stock")));
			result = juridical;
		}
		result.setDeleted(false);
		result.setLocation(this.locationImporter.process(entry));
		results.put(entry.get("number"), result);
		return result;
	}

	public static List<String> fields() {
		List<String> fields = new LinkedList<>();
		Collections.addAll(fields, "number", "type", "juridical_name", "juridical_id_number", "juridical_reg_number",
				"juridical_joint_stock", "natural_first_name", "natural_last_name", "natural_personal_number",
				"natural_id_card_type", "natural_id_card_number");
		fields.addAll(LocationImporter.fields());
		return fields;
	}

}
