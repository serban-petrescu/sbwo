package spet.sbwo.control.controller.user;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.*;
import spet.sbwo.control.channel.user.UserInfoChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.view.UserPlain;

import java.util.List;

public class ManagementController extends BaseActionExecutor {
    private static final String INITIAL_CREDENT = "init";

    public ManagementController(IDatabaseExecutorCreator database) {
        super(database);
    }

    public void registerUser(String username) {
        executeAndCommit(username, new RegisterUser(), username);
    }

    public void activateUser(String username, boolean active) {
        executeAndCommit(username, new ActivateUser(), active);
    }

    public void resetUserPassword(String username) {
        changeUserPassword(username, INITIAL_CREDENT);
    }

    public void changeUserPassword(String username, String password) {
        executeAndCommit(username, new ChangePassword(), password);
    }

    public List<UserPlain> listAllPlains() {
        return execute(new ReadAllUserPlains(), null);
    }

    public UserInfoChannel readInfo(String username) {
        return execute(username, new ReadCurrentUserInfo(), null);
    }

}
