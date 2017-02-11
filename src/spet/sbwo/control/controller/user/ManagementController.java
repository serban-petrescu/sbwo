package spet.sbwo.control.controller.user;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.ActivateAction;
import spet.sbwo.control.action.user.ChangePasswordAction;
import spet.sbwo.control.action.user.ReadAllPlainsAction;
import spet.sbwo.control.action.user.RegisterAction;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.view.UserPlain;

public class ManagementController extends BaseActionExecutor {
	private static final String INITIAL_CREDENT = "init";

	public ManagementController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public void registerUser(String username) throws ControlException {
		executeAndCommit(username, new RegisterAction(), username);
	}

	public void activateUser(String username, boolean active) throws ControlException {
		executeAndCommit(username, new ActivateAction(), active);
	}

	public void resetUserPassword(String username) throws ControlException {
		changeUserPassword(username, INITIAL_CREDENT);
	}

	public void changeUserPassword(String username, String password) throws ControlException {
		executeAndCommit(username, new ChangePasswordAction(), password);
	}

	public List<UserPlain> listAllPlains() throws ControlException {
		return execute(new ReadAllPlainsAction(), null);
	}

}
