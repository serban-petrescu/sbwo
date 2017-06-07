package spet.sbwo.control.mapper.person;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.person.PersonTelephoneChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.data.table.PersonTelephone;

import static spet.sbwo.control.mapper.Utils.NOT_NULL;

class PersonTelephoneMapper implements IMapper<PersonTelephone, PersonTelephoneChannel> {
    private static final ModelMapper mapper = new ModelMapper();
    static {
        mapper.addMappings(new PropertyMap<PersonTelephoneChannel, PersonTelephone>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                when(NOT_NULL).map(source.getName(), destination.getName());
                when(NOT_NULL).map(source.getTelephone(), destination.getTelephone());
                when(NOT_NULL).map(source.isPrimary(), destination.isPrimary());
            }
        });
    }

    PersonTelephoneMapper() {
        super();
    }

    @Override
    public PersonTelephoneChannel toChannel(PersonTelephone entity) {
        return mapper.map(entity, PersonTelephoneChannel.class);
    }

    @Override
    public PersonTelephone toEntity(PersonTelephoneChannel channel) {
        return mapper.map(channel, PersonTelephone.class);
    }

    @Override
    public void mergeIntoEntity(PersonTelephoneChannel channel, PersonTelephone entity) {
        mapper.map(channel, entity);
    }
}
