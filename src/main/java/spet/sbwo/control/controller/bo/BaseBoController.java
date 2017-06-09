package spet.sbwo.control.controller.bo;

import spet.sbwo.config.ControlEntry;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.bo.base.*;
import spet.sbwo.control.channel.base.JournalChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.base.JournalizedBaseEntity;

import java.time.Duration;

abstract class BaseBoController<E extends JournalizedBaseEntity, C extends JournalChannel> extends BaseActionExecutor
    implements IBoController<C> {
    protected final ControlEntry config;

    public BaseBoController(IDatabaseExecutorCreator database, ControlEntry config) {
        super(database);
        this.config = config;
    }

    @Override
    public int create(C data, String username) {
        return executeAndCommit(username, createAction(), data).getId();
    }

    @Override
    public C read(int id) {
        return execute(readAction(), id);
    }

    @Override
    public void update(int id, C data, String username) {
        data.setId(id);
        executeAndCommit(username, updateAction(), data);
    }

    @Override
    public void delete(int id, String username) {
        executeAndCommit(username, deleteAction(config.getDirectDeleteInterval()), id);
    }

    @Override
    public void restore(int id, String username) {
        executeAndCommit(username, restoreAction(), id);
    }

    protected abstract CreateEntity<E, C> createAction();

    protected abstract ReadEntity<E, C> readAction();

    protected abstract UpdateEntity<E, C> updateAction();

    protected abstract DeleteEntity<E> deleteAction(Duration directDeleteInterval);

    protected abstract RestoreEntity<E> restoreAction();

}
