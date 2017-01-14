package spet.sbwo.control.importer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import spet.sbwo.data.base.BaseEntity;

abstract class BaseMapImporter<T extends BaseEntity> extends BaseImporter<T>implements IEntityProvider<T> {
	protected Map<String, T> results;

	protected BaseMapImporter() {
		results = new HashMap<>();
	}

	@Override
	protected Collection<T> getResults() {
		return results.values();
	}

	@Override
	public T getEntity(String key) {
		return results.get(key);
	}
}
