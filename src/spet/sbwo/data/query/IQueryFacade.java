package spet.sbwo.data.query;

import spet.sbwo.data.DatabaseException;

public interface IQueryFacade<T> {

	IQueryFacade<T> where(String attribute, WhereOperator operator, Object value);

	T execute() throws DatabaseException;
}
