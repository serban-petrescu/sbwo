package spet.sbwo.control.action.base;

import spet.sbwo.control.ControlException;
import spet.sbwo.data.access.IDatabaseExecutor;

@FunctionalInterface
public interface IDatabaseAction<I, O> {

	O run(I input, IDatabaseExecutor executor) throws ControlException;
}
