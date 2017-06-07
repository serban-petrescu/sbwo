package spet.sbwo.control.mapper.person;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import spet.sbwo.control.channel.location.LocationChannel;
import spet.sbwo.control.channel.person.PersonBankAccountChannel;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.control.channel.person.PersonEmailChannel;
import spet.sbwo.control.channel.person.PersonTelephoneChannel;
import spet.sbwo.control.mapper.MockMapper;
import spet.sbwo.data.table.*;

import java.util.Comparator;

import static spet.sbwo.control.mapper.TestUtils.*;

public class PersonMapperTest {
    private PersonMapper mapper;
    private Person entity;
    private PersonChannel channel;

    @Before
    public void setupMapper() {
        mapper = new PersonMapper(new MockMapper<>(PersonBankAccount.class, PersonBankAccountChannel.class),
            new MockMapper<>(PersonEmailAddress.class, PersonEmailChannel.class),
            new MockMapper<>(PersonTelephone.class, PersonTelephoneChannel.class),
            new MockMapper<>(Location.class, LocationChannel.class),
            new MockMapper<>(Person.class, PersonChannel.class));
    }

    @Before
    public void setupEntity() {
        entity = mockEntity(Person.class, 1);
        entity.setLocation(mockEntity(Location.class, 1));
        entity.setBankAccounts(mockEntities(PersonBankAccount.class, 1, 2, 3));
        entity.setTelephones(mockEntities(PersonTelephone.class, 1, 2));
        entity.setEmailAddresses(mockEntities(PersonEmailAddress.class, 1, 2, 3, 4));
    }

    @Before
    public void setupChannel() {
        channel = mockChannel(PersonChannel.class, 1);
        channel.setLocation(mockChannel(LocationChannel.class, 1));
        channel.setBankAccounts(mockChannels(PersonBankAccountChannel.class, 2, 3, 4));
        channel.setTelephones(mockChannels(PersonTelephoneChannel.class, 3));
        channel.setEmailAddresses(mockChannels(PersonEmailChannel.class, 1, 2, 3, 4));
    }

    @Test
    public void testMerge() {
        mapper.mergeIntoEntity(channel, entity);
        entity.getBankAccounts().sort(Comparator.comparingInt(PersonBankAccount::getId));
        entity.getTelephones().sort(Comparator.comparingInt(PersonTelephone::getId));
        entity.getEmailAddresses().sort(Comparator.comparingInt(PersonEmailAddress::getId));

        assertEquals(1, entity.getId());
        assertEquals(1, entity.getLocation().getId());

        assertEquals(3, entity.getBankAccounts().size());
        assertEquals(2, entity.getBankAccounts().get(0).getId());
        assertEquals(3, entity.getBankAccounts().get(1).getId());
        assertEquals(4, entity.getBankAccounts().get(2).getId());

        assertEquals(1, entity.getTelephones().size());
        assertEquals(3, entity.getTelephones().get(0).getId());

        assertEquals(4, entity.getEmailAddresses().size());
        assertEquals(1, entity.getEmailAddresses().get(0).getId());
        assertEquals(2, entity.getEmailAddresses().get(1).getId());
        assertEquals(3, entity.getEmailAddresses().get(2).getId());
        assertEquals(4, entity.getEmailAddresses().get(3).getId());
    }

    @Test
    public void testToChannel() {
        PersonChannel person = mapper.toChannel(entity);
        person.getBankAccounts().sort(Comparator.comparingInt(PersonBankAccountChannel::getId));
        person.getTelephones().sort(Comparator.comparingInt(PersonTelephoneChannel::getId));
        person.getEmailAddresses().sort(Comparator.comparingInt(PersonEmailChannel::getId));

        assertEquals(1, person.getId().intValue());
        assertEquals(1, person.getLocation().getId().intValue());

        assertEquals(3, person.getBankAccounts().size());
        assertEquals(1, person.getBankAccounts().get(0).getId().intValue());
        assertEquals(2, person.getBankAccounts().get(1).getId().intValue());
        assertEquals(3, person.getBankAccounts().get(2).getId().intValue());

        assertEquals(2, person.getTelephones().size());
        assertEquals(1, person.getTelephones().get(0).getId().intValue());
        assertEquals(2, person.getTelephones().get(1).getId().intValue());

        assertEquals(4, person.getEmailAddresses().size());
        assertEquals(1, person.getEmailAddresses().get(0).getId().intValue());
        assertEquals(2, person.getEmailAddresses().get(1).getId().intValue());
        assertEquals(3, person.getEmailAddresses().get(2).getId().intValue());
        assertEquals(4, person.getEmailAddresses().get(3).getId().intValue());
    }

    @Test
    public void testToEntity() {
        Person person = mapper.toEntity(channel);
        person.getBankAccounts().sort(Comparator.comparingInt(PersonBankAccount::getId));
        person.getTelephones().sort(Comparator.comparingInt(PersonTelephone::getId));
        person.getEmailAddresses().sort(Comparator.comparingInt(PersonEmailAddress::getId));

        assertEquals(1, person.getId());
        assertEquals(1, person.getLocation().getId());

        assertEquals(3, person.getBankAccounts().size());
        assertEquals(2, person.getBankAccounts().get(0).getId());
        assertEquals(3, person.getBankAccounts().get(1).getId());
        assertEquals(4, person.getBankAccounts().get(2).getId());

        assertEquals(1, person.getTelephones().size());
        assertEquals(3, person.getTelephones().get(0).getId());

        assertEquals(4, person.getEmailAddresses().size());
        assertEquals(1, person.getEmailAddresses().get(0).getId());
        assertEquals(2, person.getEmailAddresses().get(1).getId());
        assertEquals(3, person.getEmailAddresses().get(2).getId());
        assertEquals(4, person.getEmailAddresses().get(3).getId());
    }



}
