package spet.sbwo.control.mapper.person;

import org.junit.Before;
import org.junit.Test;
import spet.sbwo.control.channel.person.PersonTelephoneChannel;
import spet.sbwo.data.table.PersonTelephone;

import static org.junit.Assert.assertEquals;

public class PersonTelephoneMapperTest {
    private PersonTelephone entity;
    private PersonTelephoneChannel fullChannel;
    private PersonTelephoneChannel nullChannel;
    private final PersonTelephoneMapper mapper = new PersonTelephoneMapper();

    private static PersonTelephone createEntity() {
        PersonTelephone entity = new PersonTelephone();
        entity.setId(1);
        entity.setName("My Phone");
        entity.setTelephone("123 123 123");
        entity.setPrimary(true);
        return entity;
    }

    private static PersonTelephoneChannel createChannel() {
        PersonTelephoneChannel fullChannel = new PersonTelephoneChannel();
        fullChannel.setId(1);
        fullChannel.setName("Secondary");
        fullChannel.setTelephone("321 321 321");
        fullChannel.setPrimary(false);
        return fullChannel;
    }

    @Before
    public void setupEntity() {
        entity = createEntity();
        fullChannel = createChannel();
        nullChannel = new PersonTelephoneChannel();
    }

    @Test
    public void testFullMerge() {
        mapper.mergeIntoEntity(fullChannel, entity);
        assertEquals(1, entity.getId());
        assertEquals("Secondary", entity.getName());
        assertEquals("321 321 321", entity.getTelephone());
        assertEquals(false, entity.isPrimary());
    }

    @Test
    public void testNullMerge() {
        mapper.mergeIntoEntity(nullChannel, entity);
        assertEquals(1, entity.getId());
        assertEquals("My Phone", entity.getName());
        assertEquals("123 123 123", entity.getTelephone());
        assertEquals(true, entity.isPrimary());
    }

    @Test
    public void testToChannel() {
        PersonTelephoneChannel currentChannel = mapper.toChannel(entity);
        assertEquals(1, currentChannel.getId().intValue());
        assertEquals("My Phone", currentChannel.getName());
        assertEquals("123 123 123", currentChannel.getTelephone());
        assertEquals(true, currentChannel.isPrimary());
    }

    @Test
    public void testToEntity() {
        PersonTelephone currentEntity = mapper.toEntity(fullChannel);
        assertEquals("Secondary", currentEntity.getName());
        assertEquals("321 321 321", currentEntity.getTelephone());
        assertEquals(false, currentEntity.isPrimary());
    }


}
