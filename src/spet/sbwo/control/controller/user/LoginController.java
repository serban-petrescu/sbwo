package spet.sbwo.control.controller.user;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.EncryptPasswordAction;
import spet.sbwo.control.action.user.EncryptedPasswordMatchesAction;
import spet.sbwo.control.action.user.PlainPasswordMatchesAction;
import spet.sbwo.control.action.user.UserExistsAction;
import spet.sbwo.control.util.ILoginProvider;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class LoginController extends BaseActionExecutor implements ILoginProvider {

	public LoginController(IDatabaseExecutorCreator database) {
		super(database);
	}

	@Override
	public boolean userExists(String username) {
		return suppress(() -> execute(username, new UserExistsAction(), null), false);
	}

	@Override
	public boolean passwordMatchesPlain(String username, String password) {
		return suppress(() -> executeAndCommit(username, new PlainPasswordMatchesAction(), password), false);
	}

	@Override
	public boolean passwordMatchesEncrypted(String username, String password) {
		return suppress(() -> executeAndCommit(username, new EncryptedPasswordMatchesAction(), password), false);
	}

	@Override
	public String encryptPassword(String username, String input) {
		return suppress(() -> execute(username, new EncryptPasswordAction(), input), null);
	}

}
