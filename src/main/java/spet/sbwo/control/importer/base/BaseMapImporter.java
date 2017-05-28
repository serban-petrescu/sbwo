package spet.sbwo.control.importer.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import spet.sbwo.control.importer.IEntityProvider;
import spet.sbwo.data.base.BaseEntity;

public abstract class BaseMapImporter<T extends BaseEntity> extends BaseImporter<T>implements IEntityProvider<T> {
	protected Map<String, T> results;

	protected BaseMapImporter() {
		results = new HashMap<>();
	}

	@Override
	public Collection<T> getResults() {
		return results.values();
	}

	@Override
	public T getEntity(String key) {
		return results.get(key);
	}
}
