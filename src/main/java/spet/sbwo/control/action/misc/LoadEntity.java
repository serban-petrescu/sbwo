package spet.sbwo.control.action.misc;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.data.access.IDatabaseExecutor;

public class LoadEntity<K, T> extends BaseDatabaseAction<K, T> {
    protected final Class<T> clazz;

    protected LoadEntity(Class<T> clazz) {
        super(clazz);
        this.clazz = clazz;
    }

    @Override
    protected T doRun(K input, IDatabaseExecutor executor) {
        return executor.find(clazz, input);
    }

}
