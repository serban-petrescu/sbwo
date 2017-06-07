package spet.sbwo.control.mapper.person;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.person.PersonEmailChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.Utils;
import spet.sbwo.data.table.PersonEmailAddress;

class PersonEmailMapper implements IMapper<PersonEmailAddress, PersonEmailChannel> {
    private static final ModelMapper mapper = new ModelMapper();
    static {
        mapper.addMappings(new PropertyMap<PersonEmailChannel, PersonEmailAddress>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                when(Utils.NOT_NULL).map(source.getName(), destination.getName());
                when(Utils.NOT_NULL).map(source.getEmail(), destination.getEmail());
                when(Utils.NOT_NULL).map(source.isPrimary(), destination.isPrimary());
            }
        });
    }

    PersonEmailMapper() {
        super();
    }

    @Override
    public PersonEmailChannel toChannel(PersonEmailAddress entity) {
        return mapper.map(entity, PersonEmailChannel.class);
    }

    @Override
    public PersonEmailAddress toEntity(PersonEmailChannel channel) {
        return mapper.map(channel, PersonEmailAddress.class);
    }

    @Override
    public void mergeIntoEntity(PersonEmailChannel channel, PersonEmailAddress entity) {
        mapper.map(channel, entity);
    }
}
