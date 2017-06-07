package spet.sbwo.control.mapper.person;

import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.data.table.PersonJuridical;

import static spet.sbwo.control.mapper.Utils.NOT_NULL;

class PersonJuridicalPropertyMap extends PropertyMap<PersonChannel, PersonJuridical> {
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
        when(NOT_NULL).map(source.getName(), destination.getName());
        when(NOT_NULL).map(source.getJointStock(), destination.getJointStock());
        when(NOT_NULL).map(source.getRegNumber(), destination.getRegNumber());
        when(NOT_NULL).map(source.getIdNumber(), destination.getIdNumber());
    }
}
