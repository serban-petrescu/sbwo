package spet.sbwo.control.util;

public interface ILoginProvider {

	boolean userExists(String username);

	boolean passwordMatchesPlain(String username, String password);
	
	boolean passwordMatchesEncrypted(String username, String password);

	String encryptPassword(String username, String input);

}
