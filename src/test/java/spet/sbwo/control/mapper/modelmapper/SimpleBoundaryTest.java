package spet.sbwo.control.mapper.modelmapper;

import org.junit.Test;

import static org.junit.Assert.*;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import spet.sbwo.control.mapper.modelmapper.model.*;

public class SimpleBoundaryTest {

    @Test
    public void testCreateSimpleEntity() {
        ParentChannel channel = ParentChannel.of(1, 2, "some", 1, "name");
        ParentEntity entity = mapper().map(channel, ParentEntity.class);
        assertEquals(1, entity.getId());
        assertEquals(2, entity.getValue());
        assertEquals("some", entity.getString());
        assertEquals(1, entity.getSibling().getId());
        assertEquals("name", entity.getSibling().getName());
    }

    @Test
    public void testCreateSimpleChannel() {
        ParentEntity entity = ParentEntity.of(1, 2, "some");
        entity.setSibling(SiblingEntity.of(1, "name"));
        ParentChannel channel = mapper().map(entity, ParentChannel.class);
        assertEquals(1, channel.getId().intValue());
        assertEquals(2, channel.getValue().intValue());
        assertEquals("some", channel.getString());
        assertEquals(1, channel.getSiblingId().intValue());
        assertEquals("name", channel.getSiblingName());
    }

    @Test
    public void testMergeIntoEntity() {
        ParentEntity entity = ParentEntity.of(1, 2, "some");
        entity.setSibling(SiblingEntity.of(1, "name"));
        ParentChannel channel = ParentChannel.of(1, 5, "else", 1, "diff");
        mapper().map(channel, entity);
        assertEquals(1, entity.getId());
        assertEquals(5, entity.getValue());
        assertEquals("else", entity.getString());
        assertEquals(1, entity.getSibling().getId());
        assertEquals("diff", entity.getSibling().getName());
    }

    @Test
    public void testMapOrdinalToEnum() {
        ChildChannel channel = ChildChannel.of(1, 1);
        ChildEntity entity = mapper().map(channel, ChildEntity.class);
        assertEquals(SomeEnum.TWO, entity.getName());
    }

    @Test
    public void testMapEnumToOrdinal() {
        ChildEntity entity = ChildEntity.of(1, SomeEnum.ONE, null);
        ChildChannel channel = mapper().map(entity, ChildChannel.class);
        assertEquals(0, channel.getName().intValue());
    }

    private static ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(new AbstractConverter<Integer, SomeEnum>() {
            @Override
            protected SomeEnum convert(Integer integer) {
                return SomeEnum.values()[integer];
            }
        });
        mapper.addConverter(new AbstractConverter<SomeEnum, Integer>() {
            @Override
            protected Integer convert(SomeEnum enumValue) {
                return enumValue.ordinal();
            }
        });
        return mapper;
    }

}
