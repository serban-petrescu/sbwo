package spet.sbwo.control.action.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.table.User;

public class PlainPasswordMatchesAction extends BasePasswordMatchesAction {
	private static final PasswordHasher HASHER = new PasswordHasher();

	@Override
	protected boolean checkPassword(User user, String password) throws ControlException {
		return HASHER.checkPassword(password, user.getPassword(), user.getSalt());
	}

}
