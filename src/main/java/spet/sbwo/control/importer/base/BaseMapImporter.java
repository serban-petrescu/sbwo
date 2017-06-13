package spet.sbwo.control.importer.base;

import spet.sbwo.data.base.BaseEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseMapImporter<T extends BaseEntity> extends BaseImporter<T> implements IEntityProvider<T> {
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

    @Override
    public T process(Map<String, String> entry) {
        T result = buildFromEntry(entry);
        results.put(entry.get("number"), result);
        return result;
    }

    protected abstract T buildFromEntry(Map<String, String> entry);
}
