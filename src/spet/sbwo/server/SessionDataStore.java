package spet.sbwo.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.server.session.AbstractSessionDataStore;
import org.eclipse.jetty.server.session.SessionData;

import spet.sbwo.control.controller.user.SessionManager;
import spet.sbwo.data.table.UserSession;

class SessionDataStore extends AbstractSessionDataStore {
	private SessionManager manager;

	public SessionDataStore(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public boolean isPassivating() {
		return true;
	}

	@Override
	public boolean exists(String id) {
		return runInContext(() -> manager.exists(id));
	}

	@Override
	public SessionData load(String id) throws Exception {
		return runInContext(() -> {
			UserSession session = manager.read(id);
			if (session != null) {
				try {
					SessionData result = new SessionData(session.getId(), session.getContextPath(),
							session.getVirtualHost(), session.getCreateTime(), session.getAccessTime(),
							session.getLastAccessTime(), session.getMaxInterval(),
							deserialize(session.getAttributes()));
					result.setExpiry(session.getExpiryTime());
					result.setLastSaved(session.getLastSaveTime());
					result.setCookieSet(session.getCookieTime());
					result.setLastNode(session.getLastNode());
					return result;
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			} else {
				throw new NullPointerException();
			}
		});
	}

	@Override
	public boolean delete(String id) {
		return runInContext(() -> manager.remove(id));
	}

	@Override
	public void doStore(String id, SessionData data, long lastSaveTime) throws Exception {
		UserSession session = new UserSession();
		session.setAccessTime(data.getAccessed());
		session.setAttributes(serialize(new HashMap<>(data.getAllAttributes())));
		session.setContextPath(data.getContextPath());
		session.setCookieTime(data.getCookieSet());
		session.setCreateTime(data.getCreated());
		session.setExpiryTime(data.getExpiry());
		session.setId(id);
		session.setLastAccessTime(data.getLastAccessed());
		session.setLastSaveTime(lastSaveTime);
		session.setLastNode(data.getLastNode());
		session.setMaxInterval(data.getMaxInactiveMs());
		session.setVirtualHost(data.getVhost());
		manager.upsert(session);
	}

	@Override
	public Set<String> doGetExpired(Set<String> candidates) {
		return new HashSet<>(manager.readAllExpired(new Date().getTime()));
	}

	protected <T> T runInContext(IExecutor<T> executor) {
		Deferred<T> deferred = new Deferred<>(executor);
		this._context.run(deferred);
		return deferred.getResult();
	}

	protected byte[] serialize(HashMap<String, Object> data) throws IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
				ObjectOutputStream obj = new ObjectOutputStream(out)) {
			obj.writeObject(data);
			obj.flush();
			return out.toByteArray();
		}
	}

	@SuppressWarnings("unchecked")
	protected HashMap<String, Object> deserialize(byte[] data) throws ClassNotFoundException, IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream obj = new ObjectInputStream(in)) {
			return (HashMap<String, Object>) obj.readObject();
		}
	}

	@FunctionalInterface
	private static interface IExecutor<T> {
		T execute();
	}

	private class Deferred<T> implements Runnable {
		private T result;
		private IExecutor<T> executor;

		public Deferred(IExecutor<T> executor) {
			this.executor = executor;
		}

		@Override
		public void run() {
			result = executor.execute();
		}

		public T getResult() {
			return result;
		}
	}
}
