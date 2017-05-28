package spet.sbwo.control.importer.base;

import java.util.Collection;
import java.util.Map;

import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.BaseEntity;

public abstract class BaseImporter<T extends BaseEntity> {

	public abstract T process(Map<String, String> entry) ;

	public abstract Collection<T> getResults();

	public void persist(IDatabaseExecutor executor)  {
		for (T entity : getResults()) {
			executor.create(entity);
		}
	}
}
