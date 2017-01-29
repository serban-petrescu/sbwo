package spet.sbwo.data.access;

import spet.sbwo.data.DatabaseException;

@FunctionalInterface
public interface IBackupCreator {

	void createBackup(String baseName) throws DatabaseException;

}
