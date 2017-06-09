package spet.sbwo.control.mapper.person;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import spet.sbwo.control.channel.person.PersonBankAccountChannel;
import spet.sbwo.data.table.PersonBankAccount;

public class PersonBankAccountMapperTest {
    private PersonBankAccount entity;
    private PersonBankAccountChannel fullChannel;
    private PersonBankAccountChannel nullChannel;
    private final PersonBankAccountMapper mapper = new PersonBankAccountMapper();

    private static PersonBankAccountChannel createChannelFull() {
        PersonBankAccountChannel bankAccount = new PersonBankAccountChannel();
        bankAccount.setId(1);
        bankAccount.setAccountNumber("4321");
        bankAccount.setBank("Other Bank");
        bankAccount.setPrimary(false);
        return bankAccount;
    }

    private static PersonBankAccount createEntity() {
        PersonBankAccount entity = new PersonBankAccount();
        entity.setId(1);
        entity.setAccountNumber("1234");
        entity.setBank("Some Bank");
        entity.setPrimary(true);
        return entity;
    }

    @Before
    public void setUp() {
        entity = createEntity();
        fullChannel = createChannelFull();
        nullChannel = new PersonBankAccountChannel();
    }

    @Test
    public void testFullMerge() {
        mapper.mergeIntoEntity(fullChannel, entity);
        assertEquals(1, entity.getId());
        assertEquals("4321", entity.getAccountNumber());
        assertEquals("Other Bank", entity.getBank());
        assertEquals(false, entity.isPrimary());
    }

    @Test
    public void testNullMerge() {
        mapper.mergeIntoEntity(nullChannel, entity);
        assertEquals(1, entity.getId());
        assertEquals("1234", entity.getAccountNumber());
        assertEquals("Some Bank", entity.getBank());
        assertEquals(true, entity.isPrimary());
    }

    @Test
    public void testToChannel() {
        PersonBankAccountChannel currentChannel = mapper.toChannel(entity);
        assertEquals(1, currentChannel.getId().intValue());
        assertEquals("1234", currentChannel.getAccountNumber());
        assertEquals("Some Bank", currentChannel.getBank());
        assertEquals(true, currentChannel.isPrimary());
    }

    @Test
    public void testToEntity() {
        PersonBankAccount currentEntity = mapper.toEntity(fullChannel);
        assertEquals("4321", currentEntity.getAccountNumber());
        assertEquals("Other Bank", currentEntity.getBank());
        assertEquals(false, currentEntity.isPrimary());
    }


}
