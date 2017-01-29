package spet.sbwo.control.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.control.channel.UserPreferenceChannel;
import spet.sbwo.control.helper.UserFavouriteHelper;
import spet.sbwo.control.helper.UserHelper;
import spet.sbwo.control.helper.UserTileHelper;
import spet.sbwo.control.util.ILoginProvider;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.view.UserPlain;

public class UserController extends BaseMainController implements ILoginProvider {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private static final String INITIAL_PASSWORD = "init";

	private final UserHelper userHelper;
	private final UserFavouriteHelper userFavouriteHelper;
	private final UserTileHelper userTileHelper;

	public UserController(IDatabaseExecutorCreator database) {
		super(database);
		userHelper = new UserHelper();
		userFavouriteHelper = new UserFavouriteHelper();
		userTileHelper = new UserTileHelper();
	}

	@Override
	public boolean userExists(String username) {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userHelper.userExists(executor, username);
		} catch (Exception e) {
			LOG.error("Forcing failed authentication because of underlying error (when checking user existance).", e);
			return false;
		}
	}

	@Override
	public boolean passwordMatchesPlain(String username, String password) {
		return passwordMatches(username, password, false);
	}

	@Override
	public boolean passwordMatchesEncrypted(String username, String password) {
		return passwordMatches(username, password, true);
	}

	@Override
	public String encryptPassword(String username, String input) {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userHelper.encryptPassword(executor, username, input);
		} catch (Exception e) {
			LOG.error("Failed to encrypt user password.", e);
			return null;
		}
	}

	public void registerUser(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			userHelper.registerUser(executor, username, INITIAL_PASSWORD);
		} catch (DatabaseException e) {
			LOG.error("Unable to register user because of a database error.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public void activateUser(String username, boolean active) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			userHelper.activateUser(executor, username, active);
		} catch (DatabaseException e) {
			LOG.error("Unable to activate user because of a database error.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public void resetUserPassword(String username) throws ControlException {
		this.changeUserPassword(username, INITIAL_PASSWORD);
	}

	public void changeUserPassword(String username, String password) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			userHelper.changePassword(executor, username, password);
		} catch (DatabaseException e) {
			LOG.error("Unable to change user password because of a database error.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public UserHomeTilesChannel getTiles(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userTileHelper.getTiles(executor, username);
		} catch (DatabaseException e) {
			LOG.error("Unable to load user tiles.");
			throw new ControlException(e, UserHomeTilesChannel.class);
		}
	}

	public UserHomeTilesChannel updateTiles(UserHomeTilesChannel data, String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userTileHelper.updateTiles(executor, data, username);
		} catch (DatabaseException e) {
			LOG.error("Unable to update user tiles.");
			throw new ControlException(e, UserHomeTilesChannel.class);
		}
	}

	public List<UserFavouriteChannel> readFavourites(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userFavouriteHelper.readFavourites(executor, username);
		} catch (DatabaseException e) {
			LOG.error("Unable to read user favourites.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public void deleteFavourite(String username, int id) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			userFavouriteHelper.deleteFavourite(executor, username, id);
		} catch (DatabaseException e) {
			LOG.error("Unable to delete user favourite.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public void addFavourite(String username, UserFavouriteChannel data) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			userFavouriteHelper.addFavourite(executor, username, data);
		} catch (DatabaseException e) {
			LOG.error("Unable to add favourite.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public List<UserFavouriteChannel> updateFavourites(String username, List<UserFavouriteChannel> input)
			throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userFavouriteHelper.updateFavourites(executor, username, input);
		} catch (DatabaseException e) {
			LOG.error("Unable to update user favourites.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public List<UserPlain> listAllPlains() throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return executor.select(UserPlain.class).execute();
		} catch (DatabaseException e) {
			LOG.error("Unable to read all users.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public UserPreferenceChannel readPreference(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userHelper.readPreferences(executor, username);
		} catch (DatabaseException e) {
			LOG.error("Unable to read user preference.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public UserPreferenceChannel updatePreference(String username, UserPreferenceChannel data) throws ControlException {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userHelper.updatePreference(executor, username, data);
		} catch (DatabaseException e) {
			LOG.error("Unable to update user preference.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	protected boolean passwordMatches(String username, String password, boolean encrypted) {
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			return userHelper.passwordMatches(executor, username, password, encrypted);
		} catch (Exception e) {
			LOG.error("Forcing failed authentication because of underlying error (when checking user password).", e);
			return false;
		}
	}
}
