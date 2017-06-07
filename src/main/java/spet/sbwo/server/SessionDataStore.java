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
import java.util.function.Supplier;

import org.eclipse.jetty.server.session.AbstractSessionDataStore;
import org.eclipse.jetty.server.session.SessionData;

import spet.sbwo.control.controller.user.ISessionManager;
import spet.sbwo.data.table.UserSession;

/**
 * Adapter class between the session manager and the Jetty specific session data
 * store.
 *
 * @author Serban Petrescu
 */
public class SessionDataStore extends AbstractSessionDataStore {
    private final ISessionManager manager;

    public SessionDataStore(ISessionManager manager) {
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
    public SessionData load(String id) {
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
                throw new IllegalArgumentException();
            }
        });
    }

    @Override
    public boolean delete(String id) {
        return runInContext(() -> manager.remove(id));
    }

    @Override
    public void doStore(String id, SessionData data, long lastSaveTime) throws IOException {
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

    /**
    * Runs a given task in the context.
    */
    protected <T> T runInContext(Supplier<T> executor) {
        Deferred<T> deferred = new Deferred<>(executor);
        this._context.run(deferred);
        return deferred.getResult();
    }

    /**
    * Serializes the session attributes.
    */
    protected byte[] serialize(HashMap<String, Object> data) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            ObjectOutputStream obj = new ObjectOutputStream(out)) {
            obj.writeObject(data);
            obj.flush();
            return out.toByteArray();
        }
    }

    /**
    * Deserializes the session attributes.
    */
    @SuppressWarnings("unchecked")
    protected HashMap<String, Object> deserialize(byte[] data) throws ClassNotFoundException, IOException {
        try (ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream obj = new ObjectInputStream(in)) {
            return (HashMap<String, Object>) obj.readObject();
        }
    }

    /**
    * Utility class for executing a task and keeping a deferred reference to
    * the result.
    */
    private class Deferred<T> implements Runnable {
        private T result;
        private final Supplier<T> supplier;

        public Deferred(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public void run() {
            result = supplier.get();
        }

        public T getResult() {
            return result;
        }
    }
}
