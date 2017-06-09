package spet.sbwo.control.mapper.person;

import spet.sbwo.control.channel.location.LocationChannel;
import spet.sbwo.control.channel.person.PersonBankAccountChannel;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.control.channel.person.PersonEmailChannel;
import spet.sbwo.control.channel.person.PersonTelephoneChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.location.LocationMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.*;

public class PersonMapper implements IMapper<Person, PersonChannel> {
    private final IMapper<PersonBankAccount, PersonBankAccountChannel> bankAccountMapper;
    private final IMapper<PersonEmailAddress, PersonEmailChannel> emailMapper;
    private final IMapper<PersonTelephone, PersonTelephoneChannel> phoneMapper;
    private final IMapper<Location, LocationChannel> locationMapper;
    private final IMapper<Person, PersonChannel> plainMapper;

    PersonMapper(IMapper<PersonBankAccount, PersonBankAccountChannel> bankAccountMapper,
                 IMapper<PersonEmailAddress, PersonEmailChannel> emailMapper,
                 IMapper<PersonTelephone, PersonTelephoneChannel> phoneMapper,
                 IMapper<Location, LocationChannel> locationMapper,
                 IMapper<Person, PersonChannel> plainMapper) {
        this.bankAccountMapper = bankAccountMapper;
        this.emailMapper = emailMapper;
        this.phoneMapper = phoneMapper;
        this.locationMapper = locationMapper;
        this.plainMapper = plainMapper;
    }

    public static IMapper<Person, PersonChannel> newInstance(IDatabaseExecutor executor) {
        return new PersonMapper(new PersonBankAccountMapper(),
            new PersonEmailMapper(), new PersonTelephoneMapper(),
            LocationMapper.newInstance(executor), new PersonPlainMapper());
    }

    @Override
    public PersonChannel toChannel(Person entity) {
        PersonChannel channel = plainMapper.toChannel(entity);
        channel.setLocation(locationMapper.toChannel(entity.getLocation()));
        channel.setBankAccounts(bankAccountMapper.toChannels(entity.getBankAccounts()));
        channel.setTelephones(phoneMapper.toChannels(entity.getTelephones()));
        channel.setEmailAddresses(emailMapper.toChannels(entity.getEmailAddresses()));
        return channel;
    }

    @Override
    public Person toEntity(PersonChannel channel) {
        Person entity = plainMapper.toEntity(channel);
        entity.setLocation(locationMapper.toEntity(channel.getLocation()));
        entity.setBankAccounts(bankAccountMapper.toEntities(channel.getBankAccounts()));
        entity.setTelephones(phoneMapper.toEntities(channel.getTelephones()));
        entity.setEmailAddresses(emailMapper.toEntities(channel.getEmailAddresses()));
        return entity;
    }

    @Override
    public void mergeIntoEntity(PersonChannel channel, Person entity) {
        plainMapper.mergeIntoEntity(channel, entity);
        locationMapper.mergeIntoEntity(channel.getLocation(), entity.getLocation());
        bankAccountMapper.mergeIntoEntities(channel.getBankAccounts(), entity.getBankAccounts());
        emailMapper.mergeIntoEntities(channel.getEmailAddresses(), entity.getEmailAddresses());
        phoneMapper.mergeIntoEntities(channel.getTelephones(), entity.getTelephones());
    }
}
