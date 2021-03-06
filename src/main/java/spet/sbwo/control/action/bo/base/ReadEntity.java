package spet.sbwo.control.action.bo.base;

import spet.sbwo.control.channel.base.JournalChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;

public abstract class ReadEntity<T extends JournalizedBaseEntity, C extends JournalChannel>
    extends BaseBoAction<T, Integer, C> {

    protected ReadEntity(Class<T> entity, Class<C> channel) {
        super(entity, channel);
    }

    @Override
    protected Integer keyFromInput(Integer input) {
        return input;
    }

    protected abstract IMapper<T, C> mapper(IDatabaseExecutor executor);

    @Override
    protected C doRun(Integer input, T t, IDatabaseExecutor executor) {
        return mapper(executor).toChannel(t);
    }

}
