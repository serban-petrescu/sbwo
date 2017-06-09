package spet.sbwo.control.mapper.person;

import org.junit.Before;
import org.junit.Test;
import spet.sbwo.control.channel.person.PersonEmailChannel;
import spet.sbwo.data.table.PersonEmailAddress;

import static org.junit.Assert.assertEquals;

public class PersonEmailMapperTest {
    private PersonEmailAddress entity;
    private PersonEmailChannel fullChannel;
    private PersonEmailChannel nullChannel;
    private final PersonEmailMapper mapper = new PersonEmailMapper();

    private static PersonEmailAddress createEntity() {
        PersonEmailAddress entity = new PersonEmailAddress();
        entity.setId(1);
        entity.setName("My Email");
        entity.setEmail("john.doe@example.com");
        entity.setPrimary(true);
        return entity;
    }

    private static PersonEmailChannel createFullChannel() {
        PersonEmailChannel fullChannel = new PersonEmailChannel();
        fullChannel.setId(1);
        fullChannel.setName("Secondary");
        fullChannel.setEmail("jdoe@gmail.com");
        fullChannel.setPrimary(false);
        return fullChannel;
    }

    @Before
    public void setUp() {
        entity = createEntity();
        fullChannel = createFullChannel();
        nullChannel = new PersonEmailChannel();
    }

    @Test
    public void testFullMerge() {
        mapper.mergeIntoEntity(fullChannel, entity);
        assertEquals(1, entity.getId());
        assertEquals("Secondary", entity.getName());
        assertEquals("jdoe@gmail.com", entity.getEmail());
        assertEquals(false, entity.isPrimary());
    }

    @Test
    public void testNullMerge() {
        mapper.mergeIntoEntity(nullChannel, entity);
        assertEquals(1, entity.getId());
        assertEquals("My Email", entity.getName());
        assertEquals("john.doe@example.com", entity.getEmail());
        assertEquals(true, entity.isPrimary());
    }

    @Test
    public void testToChannel() {
        PersonEmailChannel currentChannel = mapper.toChannel(entity);
        assertEquals(1, currentChannel.getId().intValue());
        assertEquals("My Email", currentChannel.getName());
        assertEquals("john.doe@example.com", currentChannel.getEmail());
        assertEquals(true, currentChannel.isPrimary());
    }

    @Test
    public void testToEntity() {
        PersonEmailAddress currentEntity = mapper.toEntity(fullChannel);
        assertEquals("Secondary", currentEntity.getName());
        assertEquals("jdoe@gmail.com", currentEntity.getEmail());
        assertEquals(false, currentEntity.isPrimary());
    }


}
