package spet.sbwo.control.controller.user;

import spet.sbwo.data.table.UserSession;

import java.util.List;

public interface ISessionManager {

    boolean exists(String id);

    UserSession read(String id);

    boolean remove(String id);

    void upsert(UserSession session);

    List<String> readAllExpired(long ts);

}
