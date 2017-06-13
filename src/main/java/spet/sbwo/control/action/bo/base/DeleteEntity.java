package spet.sbwo.control.action.bo.base;

import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

import java.time.Duration;
import java.time.LocalDateTime;

public class DeleteEntity<T extends JournalizedBaseEntity> extends BaseUserBoAction<T, Integer, Void> {
    protected final Duration directDeleteInterval;

    public DeleteEntity(Class<T> entity, Class<?> channel) {
        this(entity, channel, null);
    }

    public DeleteEntity(Class<T> entity, Class<?> channel, Duration directDeleteInterval) {
        super(entity, channel, true);
        this.directDeleteInterval = directDeleteInterval;
    }

    @Override
    protected Void doRun(Integer id, T t, IDatabaseExecutor executor, User user) {
        if (shouldDeleteDirectly(t)) {
            delete(executor, t);
        } else {
            mark(user, t);
        }
        return null;
    }

    protected void delete(IDatabaseExecutor executor, T t) {
        executor.delete(t);
    }

    protected void mark(User user, T t) {
        t.setDeleted(true);
        changed(user, t);
    }

    private boolean shouldDeleteDirectly(JournalizedBaseEntity entity) {
        return directDeleteInterval == null
            || directDeleteInterval.compareTo(Duration.between(entity.getCreatedOn(), LocalDateTime.now())) > 0;
    }

    @Override
    protected Integer keyFromInput(Integer input) {
        return input;
    }
}
