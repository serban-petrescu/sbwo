package spet.sbwo.control.mapper.modelmapper;

import org.junit.Test;

import static org.junit.Assert.*;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.mapper.modelmapper.model.*;

public class NullableBoundaryTest {

    @Test
    public void testNumeric() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        ParentChannel channel = ParentChannel.of(2, null, "diff");
        mapper().map(channel, entity);
        assertEquals(1, entity.getValue());
        assertEquals(2, entity.getId());
    }

    @Test
    public void testNumericWithoutNull() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        ParentChannel channel = ParentChannel.of(2, 3, "diff");
        mapper().map(channel, entity);
        assertEquals(3, entity.getValue());
        assertEquals(2, entity.getId());
    }

    @Test
    public void testString() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        ParentChannel channel = ParentChannel.of(1, 2, null);
        mapper().map(channel, entity);
        assertEquals(2, entity.getValue());
        assertEquals("some", entity.getString());
    }

    @Test
    public void testStringWithoutNull() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        ParentChannel channel = ParentChannel.of(1, 2, "diff");
        mapper().map(channel, entity);
        assertEquals(2, entity.getValue());
        assertEquals("diff", entity.getString());
    }

    @Test
    public void testEnum() {
        ChildEntity entity = ChildEntity.of(1, SomeEnum.ONE, null);
        ChildChannel channel = ChildChannel.of(2, null);
        mapper().map(channel, entity);
        assertEquals(2, entity.getId());
        assertEquals(SomeEnum.ONE, entity.getName());
    }

    @Test
    public void testEnumWithoutNull() {
        ChildEntity entity = ChildEntity.of(1, SomeEnum.ONE, null);
        ChildChannel channel = ChildChannel.of(2, 1);
        mapper().map(channel, entity);
        assertEquals(2, entity.getId());
        assertEquals(SomeEnum.TWO, entity.getName());
    }

    private static ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        Converter<Integer, SomeEnum> converter = new AbstractConverter<Integer, SomeEnum>() {
            @Override
            protected SomeEnum convert(Integer integer) {
                return SomeEnum.values()[integer];
            }
        };
        mapper.addConverter(converter);
        mapper.addMappings(new PropertyMap<ParentChannel, ParentEntity>() {
            @Override
            protected void configure() {
                when(c -> c.getSource() != null).map().setValue(source.getValue());
                when(c -> c.getSource() != null).map().setString(source.getString());
            }
        });
        mapper.addMappings(new PropertyMap<ChildChannel, ChildEntity>() {
            @Override
            protected void configure() {
                when(c -> c.getSource() != null).using(converter).map(source.getName(), destination.getName());
            }
        });
        return mapper;
    }
}
