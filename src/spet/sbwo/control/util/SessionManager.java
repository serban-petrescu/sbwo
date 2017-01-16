package spet.sbwo.control.util;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.WhereOperator;
import spet.sbwo.data.table.UserSession;

public class SessionManager {
	private static final Logger LOG = LoggerFactory.getLogger(SessionManager.class);
	private DatabaseFacade database;

	public SessionManager(DatabaseFacade database) {
		this.database = database;
	}

	public boolean exists(String id) {
		try (IDatabaseExecutor executor = database.buildExecutor(false)) {
			return executor.find(UserSession.class, id) != null;
		} catch (DatabaseException e) {
			LOG.error("Unable to check session {} for existance.", id);
			return false;
		}
	}

	public UserSession read(String id) {
		try (IDatabaseExecutor executor = database.buildExecutor(false)) {
			return executor.find(UserSession.class, id);
		} catch (DatabaseException e) {
			LOG.error("Unable to load session {}.", id);
			return null;
		}
	}

	public boolean remove(String id) {
		try (IDatabaseExecutor executor = database.buildExecutor(false)) {
			UserSession session = executor.find(UserSession.class, id);
			if (session != null) {
				executor.delete(session);
				executor.commit();
				return true;
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to delete session {}.", id);
		}
		return false;
	}

	public void upsert(UserSession session) {
		try (IDatabaseExecutor executor = database.buildExecutor(false)) {
			UserSession attached = executor.find(UserSession.class, session.getId());
			if (attached == null) {
				executor.create(session);
			} else {
				executor.update(session);
			}
			executor.commit();
		} catch (DatabaseException e) {
			LOG.error("Unable to update session {}.", session.getId());
		}
	}

	public List<UserSession> readAllExpired(long ts) {
		try (IDatabaseExecutor executor = database.buildExecutor(false)) {
			return executor.select(UserSession.class).where("expiryTime", WhereOperator.LE, ts).execute();
		} catch (DatabaseException e) {
			LOG.error("Unable to read all expired sessions.");
			return new LinkedList<>();
		}
	}
}
