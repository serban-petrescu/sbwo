package spet.sbwo.control.action.bo.base;

import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public class RestoreEntity<T extends JournalizedBaseEntity> extends BaseUserBoAction<T, Integer, Void> {

    public RestoreEntity(Class<T> entity, Class<?> channel) {
        super(entity, channel, true);
    }

    @Override
    protected Void doRun(Integer id, T t, IDatabaseExecutor executor, User user) {
        t.setDeleted(false);
        changed(user, t);
        return null;
    }

    @Override
    protected Integer keyFromInput(Integer input) {
        return input;
    }

}
