package spet.sbwo.control.mapper.person;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.Utils.FromEnumConverter;
import spet.sbwo.control.mapper.Utils.ToEnumConverter;
import spet.sbwo.data.domain.IdentityCardType;
import spet.sbwo.data.domain.PersonType;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonJuridical;
import spet.sbwo.data.table.PersonNatural;

class PersonPlainMapper implements IMapper<Person, PersonChannel> {
    private static final ModelMapper naturalMapper = new ModelMapper();
    private static final ModelMapper juridicalMapper = new ModelMapper();

    static {
        naturalMapper.addMappings(new PersonNaturalPropertyMap());
        naturalMapper.addMappings(new PropertyMap<PersonNatural, PersonChannel>() {
            @Override
            protected void configure() {
                skip(destination.getName());
            }
        });
        naturalMapper.addConverter(new ToEnumConverter<PersonType>(PersonType.class) {
        });
        naturalMapper.addConverter(new FromEnumConverter<PersonType>() {
        });
        naturalMapper.addConverter(new ToEnumConverter<IdentityCardType>(IdentityCardType.class) {
        });
        naturalMapper.addConverter(new FromEnumConverter<IdentityCardType>() {
        });
    }

    static {
        juridicalMapper.addMappings(new PersonJuridicalPropertyMap());
        juridicalMapper.addConverter(new ToEnumConverter<PersonType>(PersonType.class) {
        });
        juridicalMapper.addConverter(new FromEnumConverter<PersonType>() {
        });
    }

    PersonPlainMapper() {
        super();
    }

    @Override
    public PersonChannel toChannel(Person entity) {
        if (entity instanceof PersonJuridical) {
            return juridicalMapper.map(entity, PersonChannel.class);
        } else {
            return naturalMapper.map(entity, PersonChannel.class);
        }
    }

    @Override
    public Person toEntity(PersonChannel channel) {
        if (channel.getType() == PersonType.JURIDICAL.ordinal()) {
            return juridicalMapper.map(channel, PersonJuridical.class);
        } else {
            return naturalMapper.map(channel, PersonNatural.class);
        }
    }

    @Override
    public void mergeIntoEntity(PersonChannel channel, Person entity) {
        if (entity instanceof PersonJuridical) {
            juridicalMapper.map(channel, entity);
        } else {
            naturalMapper.map(channel, entity);
        }
    }
}
