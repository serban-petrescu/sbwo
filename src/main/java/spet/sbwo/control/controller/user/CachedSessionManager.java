package spet.sbwo.control.controller.user;

import spet.sbwo.data.table.UserSession;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CachedSessionManager implements ISessionManager {
    private final SessionManager inner;
    private final ConcurrentMap<String, UserSession> sessions;

    public CachedSessionManager(SessionManager inner) {
        this.inner = inner;
        this.sessions = new ConcurrentHashMap<>();
    }

    @Override
    public boolean exists(String id) {
        return sessions.containsKey(id) || inner.exists(id);
    }

    @Override
    public UserSession read(String id) {
        return sessions.computeIfAbsent(id, inner::read);
    }

    @Override
    public boolean remove(String id) {
        sessions.remove(id);
        return inner.remove(id);
    }

    @Override
    public void upsert(UserSession session) {
        sessions.compute(session.getId(), (k, v) -> {
            if (v == null) {
                inner.upsert(session);
            }
            return session;
        });
    }

    @Override
    public List<String> readAllExpired(long ts) {
        return inner.readAllExpired(ts);
    }

    public void flush() {
        sessions.values().forEach(inner::upsert);
    }

    public void stop() {
        flush();
    }

}
