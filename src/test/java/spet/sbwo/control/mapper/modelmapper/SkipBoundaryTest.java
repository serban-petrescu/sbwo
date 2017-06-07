package spet.sbwo.control.mapper.modelmapper;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.mapper.modelmapper.model.*;

import static org.junit.Assert.*;

public class SkipBoundaryTest {

    @Test
    public void testSimplePropertyMerge() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        ParentChannel channel = ParentChannel.of(2, 3, "diff");
        mapper().map(channel, entity);
        assertEquals(1, entity.getId());
        assertEquals(3, entity.getValue());
    }

    @Test
    public void testSimplePropertyMergeReverseDoesNotSkip() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        ParentChannel channel = ParentChannel.of(2, 3, "diff");
        mapper().map(entity, channel);
        assertEquals(1, channel.getId().intValue());
        assertEquals(1, channel.getValue().intValue());
    }

    @Test
    public void testSimplePropertyCreate() {
        ParentChannel channel = ParentChannel.of(2, 3, "diff");
        ParentEntity entity = mapper().map(channel, ParentEntity.class);
        assertEquals(0, entity.getId());
        assertEquals(3, entity.getValue());
    }

    @Test
    public void testComplexPropertyMerge() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        entity.setSibling(SiblingEntity.of(1, "name"));
        ParentChannel channel = ParentChannel.of(2, 3, "diff", 5, "full");
        mapper().map(channel, entity);
        assertEquals(1, entity.getSibling().getId());
        assertEquals("name", entity.getSibling().getName());
    }

    @Test
    public void testComplexPropertyMergeReverseDoesNotSkip() {
        ParentEntity entity = ParentEntity.of(1, 1, "some");
        entity.setSibling(SiblingEntity.of(1, "name"));
        ParentChannel channel = ParentChannel.of(2, 3, "diff", 5, "full");
        mapper().map(entity, channel);
        assertEquals(1, entity.getSibling().getId());
        assertEquals("name", entity.getSibling().getName());
    }

    private static ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<ParentChannel, ParentEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getSibling());
            }
        });
        return mapper;
    }
}
