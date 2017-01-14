package spet.sbwo.data.access;

import java.util.List;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.base.BaseEntity;

public interface IDatabaseExecutor extends AutoCloseable {

	void start() throws DatabaseException;

	void commit() throws DatabaseException;

	void commit(boolean start) throws DatabaseException;

	void rollback() throws DatabaseException;

	void rollback(boolean start) throws DatabaseException;

	<T extends BaseEntity> void update(T entity) throws DatabaseException;

	<T extends BaseEntity> void delete(T entity) throws DatabaseException;

	<T extends BaseEntity> void create(T entity) throws DatabaseException;

	<T> IQueryFacade<List<T>> select(Class<T> entity);

	<T> IQueryFacade<T> selectSingle(Class<T> entity);

	<T> T find(Class<T> clazz, Object id) throws DatabaseException;

	<T> IQueryFacade<Long> count(Class<T> entity);

	void close();
}