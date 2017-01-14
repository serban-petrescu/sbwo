package spet.sbwo.data.access;

import spet.sbwo.data.DatabaseException;

public interface IQueryFacade<T> {

	IQueryFacade<T> where(String attribute, WhereOperator operator, Object value);

	T execute() throws DatabaseException;
}
