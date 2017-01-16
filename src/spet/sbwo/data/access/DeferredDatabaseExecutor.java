package spet.sbwo.data.access;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import spet.sbwo.data.DatabaseException;

class DeferredDatabaseExecutor extends BaseDatabaseExecutor {
	private List<IUpdateCommand> updates;

	DeferredDatabaseExecutor(EntityManager em) throws DatabaseException {
		super(em);
		this.updates = new LinkedList<>();
	}

	@Override
	public void commit(boolean start) throws DatabaseException {
		try {
			for (IUpdateCommand command : this.updates) {
				command.run(this.em);
			}
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
		super.commit(start);
		this.updates.clear();
	}

	@Override
	public void rollback(boolean start) throws DatabaseException {
		this.updates.clear();
		super.rollback(start);
	}

	@Override
	public <T> void update(T entity) throws DatabaseException {
		this.updates.add(this.simpleCommandBuilder.update(entity));
	}

	@Override
	public <T> void delete(T entity) throws DatabaseException {
		this.updates.add(this.simpleCommandBuilder.delete(entity));
	}

	@Override
	public <T> void create(T entity) throws DatabaseException {
		this.updates.add(this.simpleCommandBuilder.create(entity));
	}

}
