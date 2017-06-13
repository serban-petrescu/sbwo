package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.BaseBoAction;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.control.util.VCardBuilder;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.*;

public class ExportPerson extends BaseBoAction<Person, Integer, String> {

    public ExportPerson() {
        super(Person.class, PersonChannel.class);
    }

    @Override
    protected String doRun(Integer input, Person p, IDatabaseExecutor executor) {
        VCardBuilder builder = new VCardBuilder();
        builder.name(getPersonName(p));
        builder.address(p.getLocation().getAddress());
        for (PersonEmailAddress email : p.getEmailAddresses()) {
            builder.email(email.getEmail(), email.isPrimary());
        }
        for (PersonTelephone phone : p.getTelephones()) {
            builder.phone(phone.getTelephone(), phone.isPrimary());
        }
        return builder.build();
    }

    protected String getPersonName(Person p) {
        if (p instanceof PersonNatural) {
            PersonNatural natural = (PersonNatural) p;
            if (natural.getFirstName() != null && natural.getLastName() != null) {
                return natural.getFirstName() + " " + natural.getLastName();
            }
        } else {
            PersonJuridical juridical = (PersonJuridical) p;
            return juridical.getName();
        }
        return "";
    }

    @Override
    protected Integer keyFromInput(Integer input) {
        return input;
    }
}
