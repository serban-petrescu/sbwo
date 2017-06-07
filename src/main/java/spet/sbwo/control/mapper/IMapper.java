package spet.sbwo.control.mapper;

import spet.sbwo.control.channel.base.BaseChannel;
import spet.sbwo.data.base.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;

public interface IMapper<E extends BaseEntity, C extends BaseChannel> {

    C toChannel(E entity);

    E toEntity(C channel);

    void mergeIntoEntity(C channel, E entity);

    default List<C> toChannels(List<E> entities) {
        if (entities != null) {
            return entities.stream().map(this::toChannel).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    default List<E> toEntities(List<C> channels) {
        if (channels != null) {
            return channels.stream().map(this::toEntity).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    default void mergeIntoEntities(List<C> channels, List<E> entities) {
        List<C> nonNullChannels = channels == null ? Collections.emptyList() : channels;
        Map<Integer, C> channelMap = new HashMap<>();
        for (C channel : nonNullChannels) {
            channelMap.put(channel.getId(), channel);
        }
        for (Iterator<E> i = entities.iterator(); i.hasNext(); ) {
            E entity = i.next();
            C channel = channelMap.get(entity.getId());
            if (channel != null) {
                mergeIntoEntity(channel, entity);
                channelMap.remove(entity.getId());
            } else {
                i.remove();
            }
        }
        for (Map.Entry<Integer, C> entry : channelMap.entrySet()) {
            entities.add(toEntity(entry.getValue()));
        }
    }


}
