package spet.sbwo.control.util;

public interface ILoginProvider {

	boolean userExists(String username);

	boolean passwordMatches(String username, String password);

}
