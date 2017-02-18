package spet.sbwo.data.access;

import java.util.List;
import java.util.Optional;

import spet.sbwo.data.DatabaseException;

public interface IDatabaseExecutor extends AutoCloseable {

	void start() throws DatabaseException;

	void commit() throws DatabaseException;

	void commit(boolean start) throws DatabaseException;

	void rollback() throws DatabaseException;

	void rollback(boolean start) throws DatabaseException;

	<T> void update(T entity) throws DatabaseException;

	<T> void delete(T entity) throws DatabaseException;

	<T> void create(T entity) throws DatabaseException;

	<T> List<T> queryList(String name, Class<T> resultType, Object... params) throws DatabaseException;

	<T> Optional<T> querySingle(String name, Class<T> resultType, Object... params) throws DatabaseException;

	<T> T find(Class<T> clazz, Object id) throws DatabaseException;

	@Override
	void close();
}