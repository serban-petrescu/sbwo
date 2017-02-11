package spet.sbwo.control.action.base;

import spet.sbwo.control.ControlException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;

@FunctionalInterface
public interface IUserDatabaseAction<I, O> {

	O run(I input, IDatabaseExecutor executor, User user) throws ControlException;
}
