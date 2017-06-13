package spet.sbwo.control.action.bo.base;

import spet.sbwo.control.channel.base.JournalChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public abstract class UpdateEntity<T extends JournalizedBaseEntity, C extends JournalChannel>
    extends BaseUserBoAction<T, C, Void> {

    protected UpdateEntity(Class<T> entity, Class<C> channel) {
        super(entity, channel, true);
    }

    protected abstract IMapper<T, C> mapper(IDatabaseExecutor executor);

    @Override
    protected Void doRun(C input, T t, IDatabaseExecutor executor, User user) {
        IMapper<T, C> mapper = mapper(executor);
        mapper.mergeIntoEntity(input, t);
        changed(user, t);
        return null;
    }

    @Override
    protected Integer keyFromInput(C input) {
        return input.getId();
    }
}
