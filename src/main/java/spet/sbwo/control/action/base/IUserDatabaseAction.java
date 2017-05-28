package spet.sbwo.control.action.base;

import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

@FunctionalInterface
public interface IUserDatabaseAction<I, O> {

	O run(I input, IDatabaseExecutor executor, User user) ;

	default <X> IUserDatabaseAction<I, X> then(IUserDatabaseAction<O, X> second) {
		return (i, e, u) -> second.run(this.run(i, e, u), e, u);
	}

	default <X> IUserDatabaseAction<I, X> then(IDatabaseAction<O, X> second) {
		return (i, e, u) -> second.run(this.run(i, e, u), e);
	}
}
