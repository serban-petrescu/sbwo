package spet.sbwo.control.action.bo.base;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;

public abstract class BaseBoAction<T extends JournalizedBaseEntity, I, O> extends BaseDatabaseAction<I, O> {
    protected final Class<T> entity;

    protected BaseBoAction(Class<T> entity, Class<?> channel) {
        super(channel);
        this.entity = entity;
    }

    @Override
    public O doRun(I input, IDatabaseExecutor executor) {
        T t = executor.find(entity, keyFromInput(input));
        if (t != null) {
            return doRun(input, t, executor);
        } else {
            throw new ControlException(ControlError.ENTITY_NOT_FOUND, channel);
        }
    }

    protected abstract Integer keyFromInput(I input);

    protected abstract O doRun(I input, T t, IDatabaseExecutor executor);

}
