package spet.sbwo.control.controller.user;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.EncryptPassword;
import spet.sbwo.control.action.user.PasswordMatchesEncrypted;
import spet.sbwo.control.action.user.PasswordMatchesPlain;
import spet.sbwo.control.action.user.UserExists;
import spet.sbwo.control.util.ILoginProvider;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class LoginController extends BaseActionExecutor implements ILoginProvider {

	public LoginController(IDatabaseExecutorCreator database) {
		super(database);
	}

	@Override
	public boolean userExists(String username) {
		return suppress(() -> execute(username, new UserExists(), null), false);
	}

	@Override
	public boolean passwordMatchesPlain(String username, String password) {
		return suppress(() -> executeAndCommit(username, new PasswordMatchesPlain(), password), false);
	}

	@Override
	public boolean passwordMatchesEncrypted(String username, String password) {
		return suppress(() -> executeAndCommit(username, new PasswordMatchesEncrypted(), password), false);
	}

	@Override
	public String encryptPassword(String username, String input) {
		return suppress(() -> execute(username, new EncryptPassword(), input), null);
	}

}
