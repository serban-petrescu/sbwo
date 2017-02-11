package spet.sbwo.control.action;

import spet.sbwo.control.action.base.IDatabaseAction;
import spet.sbwo.control.action.base.IUserDatabaseAction;

public class Actions {
	private Actions() {
		super();
	}

	public static <I, X, O> IDatabaseAction<I, O> chain(IDatabaseAction<I, X> first, IDatabaseAction<X, O> second) {
		return (i, e) -> second.run(first.run(i, e), e);
	}

	public static <I, X, O> IUserDatabaseAction<I, O> chain(IUserDatabaseAction<I, X> first,
			IUserDatabaseAction<X, O> second) {
		return (i, e, u) -> second.run(first.run(i, e, u), e, u);
	}

	public static <I, X, O> IUserDatabaseAction<I, O> chain(IDatabaseAction<I, X> first,
			IUserDatabaseAction<X, O> second) {
		return (i, e, u) -> second.run(first.run(i, e), e, u);
	}

	public static <I, X, O> IUserDatabaseAction<I, O> chain(IUserDatabaseAction<I, X> first,
			IDatabaseAction<X, O> second) {
		return (i, e, u) -> second.run(first.run(i, e, u), e);
	}
}
