package spet.sbwo.control.importer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.base.BaseEntity;

abstract class BaseListImporter<T extends BaseEntity> extends BaseImporter<T> {
	protected List<T> results;

	protected BaseListImporter() {
		results = new LinkedList<>();
	}

	@Override
	public T process(Map<String, String> entry) throws DatabaseException {
		T result = build(entry);
		results.add(result);
		return result;
	}

	protected abstract T build(Map<String, String> entry) throws DatabaseException;

	@Override
	protected Collection<T> getResults() {
		return results;
	}
}
