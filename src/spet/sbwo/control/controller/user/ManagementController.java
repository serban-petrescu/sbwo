package spet.sbwo.control.controller.user;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.ActivateUser;
import spet.sbwo.control.action.user.ChangePassword;
import spet.sbwo.control.action.user.ReadAllUserPlains;
import spet.sbwo.control.action.user.RegisterUser;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.view.UserPlain;

public class ManagementController extends BaseActionExecutor {
	private static final String INITIAL_CREDENT = "init";

	public ManagementController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public void registerUser(String username) throws ControlException {
		executeAndCommit(username, new RegisterUser(), username);
	}

	public void activateUser(String username, boolean active) throws ControlException {
		executeAndCommit(username, new ActivateUser(), active);
	}

	public void resetUserPassword(String username) throws ControlException {
		changeUserPassword(username, INITIAL_CREDENT);
	}

	public void changeUserPassword(String username, String password) throws ControlException {
		executeAndCommit(username, new ChangePassword(), password);
	}

	public List<UserPlain> listAllPlains() throws ControlException {
		return execute(new ReadAllUserPlains(), null);
	}

}
