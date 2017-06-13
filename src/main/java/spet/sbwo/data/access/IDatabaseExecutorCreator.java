package spet.sbwo.data.access;

@FunctionalInterface
public interface IDatabaseExecutorCreator {
    IDatabaseExecutor createExecutor();
}
