package spet.sbwo.control.mapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import spet.sbwo.control.channel.base.BaseChannel;
import spet.sbwo.data.base.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IMapperTest {
    private List<BaseEntity> entities;
    private List<BaseChannel> channels;
    private final MockMapper mapper = new MockMapper();

    @Before
    public void setUpEntities() {
        entities = Stream.of(2, 3, 4).map(IMapperTest::entityFor).collect(Collectors.toList());
    }

    @Before
    public void setUpChannels() {
        channels = Stream.of(1, 3, 5).map(IMapperTest::channelFor).collect(Collectors.toList());
    }

    @Test
    public void testToEntities() {
        List<BaseEntity> currentEntities = mapper.toEntities(channels);
        List<Integer> ids = currentEntities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        assertEquals(Arrays.asList(1, 3, 5), ids);
    }

    @Test
    public void testToChannels() {
        List<BaseChannel> currentChannels = mapper.toChannels(entities);
        List<Integer> ids = currentChannels.stream().map(BaseChannel::getId).collect(Collectors.toList());
        assertEquals(Arrays.asList(2, 3, 4), ids);
    }

    @Test
    public void testMergeIntoEntities() {
        List<BaseEntity> original = new LinkedList<>(entities);
        mapper.mergeIntoEntities(channels, entities);
        entities.sort(Comparator.comparing(BaseEntity::getId));
        List<Integer> ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        assertEquals(Arrays.asList(1, 3, 5), ids);
        assertTrue(original.get(0) != entities.get(0));
        assertTrue(original.get(1) == entities.get(1));
        assertTrue(original.get(2) != entities.get(2));
    }

    private static BaseEntity entityFor(int id) {
        BaseEntity entity = new BaseEntity();
        entity.setId(id);
        return entity;
    }

    private static BaseChannel channelFor(Integer id) {
        BaseChannel channel = new BaseChannel();
        channel.setId(id);
        return channel;
    }

    public static class MockMapper implements IMapper<BaseEntity, BaseChannel> {
        @Override
        public BaseChannel toChannel(BaseEntity entity) {
            return channelFor(entity.getId());
        }

        @Override
        public BaseEntity toEntity(BaseChannel channel) {
            return entityFor(channel.getId());
        }

        @Override
        public void mergeIntoEntity(BaseChannel channel, BaseEntity entity) {
            entity.setId(channel.getId());
        }
    }
}
