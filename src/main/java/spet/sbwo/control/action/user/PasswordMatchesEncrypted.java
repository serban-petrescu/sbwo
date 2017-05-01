package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.data.table.User;

public class PasswordMatchesEncrypted extends BasePasswordMatchesAction {

	@Override
	protected boolean checkPassword(User user, String password) throws ControlException {
		return password != null && password.equals(user.getPassword());
	}

}
