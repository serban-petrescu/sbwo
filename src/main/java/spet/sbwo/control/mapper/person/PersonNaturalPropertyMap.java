package spet.sbwo.control.mapper.person;

import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.control.mapper.Utils;
import spet.sbwo.data.table.PersonNatural;

class PersonNaturalPropertyMap extends PropertyMap<PersonChannel, PersonNatural> {
    @Override
    protected void configure() {
        skip(destination.getId());
        skip(destination.getChangedBy());
        skip(destination.getChangedOn());
        skip(destination.getCreatedBy());
        skip(destination.getCreatedOn());
        skip(destination.isDeleted());
        skip(destination.getEmailAddresses());
        skip(destination.getBankAccounts());
        skip(destination.getTelephones());
        skip(destination.getLocation());
        when(Utils.NOT_NULL).map(source.getFirstName(), destination.getFirstName());
        when(Utils.NOT_NULL).map(source.getLastName(), destination.getLastName());
        when(Utils.NOT_NULL).map(source.getIdentityCardNumber(), destination.getIdentityCardNumber());
        when(Utils.NOT_NULL).map(source.getIdentityCardType(), destination.getIdentityCardType());
        when(Utils.NOT_NULL).map(source.getPersonalNumber(), destination.getPersonalNumber());
    }
}
