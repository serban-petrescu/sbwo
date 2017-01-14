package spet.sbwo.control.importer;

import java.util.Collection;
import java.util.Map;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.BaseEntity;

abstract class BaseImporter<T extends BaseEntity> {

	public abstract T process(Map<String, String> entry) throws DatabaseException;

	protected abstract Collection<T> getResults();

	protected void persist(IDatabaseExecutor executor) throws DatabaseException {
		for (T entity : getResults()) {
			executor.create(entity);
		}
	}
}
