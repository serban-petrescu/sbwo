package spet.sbwo.control.mapper.person;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.person.PersonBankAccountChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.data.table.PersonBankAccount;

import static spet.sbwo.control.mapper.Utils.NOT_NULL;

class PersonBankAccountMapper implements IMapper<PersonBankAccount, PersonBankAccountChannel> {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.addMappings(new PropertyMap<PersonBankAccountChannel, PersonBankAccount>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                when(NOT_NULL).map(source.getAccountNumber(), destination.getAccountNumber());
                when(NOT_NULL).map(source.getBank(), destination.getBank());
                when(NOT_NULL).map(source.isPrimary(), destination.isPrimary());
            }
        });
    }

    PersonBankAccountMapper() {
        super();
    }

    @Override
    public PersonBankAccountChannel toChannel(PersonBankAccount entity) {
        return mapper.map(entity, PersonBankAccountChannel.class);
    }

    @Override
    public PersonBankAccount toEntity(PersonBankAccountChannel channel) {
        return mapper.map(channel, PersonBankAccount.class);
    }

    @Override
    public void mergeIntoEntity(PersonBankAccountChannel channel, PersonBankAccount entity) {
        mapper.map(channel, entity);
    }
}
