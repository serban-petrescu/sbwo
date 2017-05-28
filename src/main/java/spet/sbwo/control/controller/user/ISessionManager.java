package spet.sbwo.control.controller.user;

import java.util.List;

import spet.sbwo.data.table.UserSession;

public interface ISessionManager {

    boolean exists(String id);

    UserSession read(String id);

    boolean remove(String id);

    void upsert(UserSession session);

    List<String> readAllExpired(long ts);

}
