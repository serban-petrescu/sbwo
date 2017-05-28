package spet.sbwo.data.access;

import java.util.List;
import java.util.Optional;

public interface IDatabaseExecutor extends AutoCloseable {

    void start();

    void commit();

    void commit(boolean start);

    void rollback();

    void rollback(boolean start);

    <T> void update(T entity);

    <T> void delete(T entity);

    <T> void create(T entity);

    <T> List<T> queryList(String name, Class<T> resultType, Object... params);

    <T> List<T> queryListLimit(String name, Class<T> resultType, int maxResults, Object... params);

    <T> Optional<T> querySingle(String name, Class<T> resultType, Object... params);

    <T> T find(Class<T> clazz, Object id);

    @Override
    void close();
}
