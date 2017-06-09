package spet.sbwo.control.action.base;

import org.slf4j.LoggerFactory;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.user.ReadByUsername;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

import java.util.concurrent.Callable;

public abstract class BaseActionExecutor {
    protected final IDatabaseExecutorCreator database;

    protected BaseActionExecutor(IDatabaseExecutorCreator database) {
        this.database = database;
    }

    protected <I, O> O execute(IDatabaseAction<I, O> action, I in) {
        try (IDatabaseExecutor executor = database.createExecutor()) {
            return action.run(in, executor);
        } catch (DatabaseException e) {
            throw new ControlException(e);
        }
    }

    protected <I, O> O execute(String username, IUserDatabaseAction<I, O> action, I in) {
        try (IDatabaseExecutor executor = database.createExecutor()) {
            return action.run(in, executor, new ReadByUsername().run(username, executor));
        } catch (DatabaseException e) {
            throw new ControlException(e);
        }
    }

    protected <I, O> O executeAndCommit(String username, IUserDatabaseAction<I, O> action, I in) {
        try (IDatabaseExecutor executor = database.createExecutor()) {
            O o = action.run(in, executor, new ReadByUsername().run(username, executor));
            executor.commit();
            return o;
        } catch (DatabaseException e) {
            throw new ControlException(e);
        }
    }

    protected <I, O> O executeAndCommit(IDatabaseAction<I, O> action, I in) {
        try (IDatabaseExecutor executor = database.createExecutor()) {
            O o = action.run(in, executor);
            executor.commit();
            return o;
        } catch (DatabaseException e) {
            throw new ControlException(e);
        }
    }

    protected <T> T suppress(Callable<T> callable) {
        return suppress(callable, null);
    }

    protected <T> T suppress(Callable<T> callable, T defaultValue) {
        try {
            return callable.call();
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("Supressed exception.", e);
            return defaultValue;
        }
    }
}
