package spet.sbwo.data.access;

import spet.sbwo.data.DatabaseException;

@FunctionalInterface
public interface IDatabaseExecutorCreator {
	IDatabaseExecutor createExecutor() throws DatabaseException;
}
