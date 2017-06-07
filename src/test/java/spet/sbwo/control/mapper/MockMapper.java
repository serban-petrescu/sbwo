package spet.sbwo.control.mapper;

import spet.sbwo.control.channel.base.BaseChannel;
import spet.sbwo.data.base.BaseEntity;

public class MockMapper<E extends BaseEntity, C extends BaseChannel> implements IMapper<E, C> {
    private final Class<E> entityClass;
    private final Class<C> channelClass;

    public MockMapper(Class<E> entityClass, Class<C> channelClass) {
        this.entityClass = entityClass;
        this.channelClass = channelClass;
    }

    @Override
    public C toChannel(E entity) {
        try {
            C channel = channelClass.newInstance();
            channel.setId(entity.getId());
            return channel;
        } catch (Exception e) {
            throw new AssertionError("Unable to instantiate channel.", e);
        }
    }

    @Override
    public E toEntity(C channel) {
        try {
            E entity = entityClass.newInstance();
            entity.setId(channel.getId());
            return entity;
        } catch (Exception e) {
            throw new AssertionError("Unable to instantiate entity.", e);
        }
    }

    @Override
    public void mergeIntoEntity(C channel, E entity) {
        // nothing to do, ids are equal by contract
    }
}
