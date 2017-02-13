package spet.sbwo.control.controller.bo;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.bo.base.CreateEntityAction;
import spet.sbwo.control.action.bo.base.DeleteEntityAction;
import spet.sbwo.control.action.bo.base.ReadEntityAction;
import spet.sbwo.control.action.bo.base.RestoreEntityAction;
import spet.sbwo.control.action.bo.base.UpdateEntityAction;
import spet.sbwo.control.channel.JournalChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.base.JournalizedBaseEntity;

abstract class BaseBoController<E extends JournalizedBaseEntity, C extends JournalChannel>
		extends BaseActionExecutor implements IBoController<C> {
	protected final int directDeleteInterval;

	public BaseBoController(IDatabaseExecutorCreator database, int directDeleteInterval) {
		super(database);
		this.directDeleteInterval = directDeleteInterval;
	}

	@Override
	public int create(C data, String username) throws ControlException {
		return executeAndCommit(username, createAction(), data);
	}

	@Override
	public C read(int id) throws ControlException {
		return execute(readAction(), id);
	}

	@Override
	public void update(int id, C data, String username) throws ControlException {
		data.setId(id);
		executeAndCommit(username, updateAction(), data);
	}

	@Override
	public void delete(int id, String username) throws ControlException {
		executeAndCommit(username, deleteAction(directDeleteInterval), id);
	}

	@Override
	public void restore(int id, String username) throws ControlException {
		executeAndCommit(username, restoreAction(), id);
	}

	protected abstract CreateEntityAction<E, C> createAction();

	protected abstract ReadEntityAction<E, C> readAction();

	protected abstract UpdateEntityAction<E, C> updateAction();

	protected abstract DeleteEntityAction<E> deleteAction(int directDeleteInterval);

	protected abstract RestoreEntityAction<E> restoreAction();

}
