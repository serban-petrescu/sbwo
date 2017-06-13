package spet.sbwo.control.action.base;

import spet.sbwo.data.access.IDatabaseExecutor;

@FunctionalInterface
public interface IDatabaseAction<I, O> {

    O run(I input, IDatabaseExecutor executor);

    default <X> IDatabaseAction<I, X> then(IDatabaseAction<O, X> second) {
        return (i, e) -> second.run(this.run(i, e), e);
    }

    default <X> IUserDatabaseAction<I, X> then(IUserDatabaseAction<O, X> second) {
        return (i, e, u) -> second.run(this.run(i, e), e, u);
    }

}
