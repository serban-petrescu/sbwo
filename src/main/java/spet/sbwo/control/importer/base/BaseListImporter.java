package spet.sbwo.control.importer.base;

import spet.sbwo.data.base.BaseEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class BaseListImporter<T extends BaseEntity> extends BaseImporter<T> {
    protected List<T> results;

    protected BaseListImporter() {
        results = new LinkedList<>();
    }

    @Override
    public T process(Map<String, String> entry) {
        T result = build(entry);
        results.add(result);
        return result;
    }

    protected abstract T build(Map<String, String> entry);

    @Override
    public Collection<T> getResults() {
        return results;
    }
}
