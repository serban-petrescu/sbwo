package spet.sbwo.control.action.user;

import spet.sbwo.data.table.User;

public class PasswordMatchesEncrypted extends BasePasswordMatchesAction {

    @Override
    protected boolean checkPassword(User user, String password) {
        return password != null && password.equals(user.getPassword());
    }

}
