package spet.sbwo.data.access;

@FunctionalInterface
public interface IBackupCreator {

    void createBackup(String baseName);

}
