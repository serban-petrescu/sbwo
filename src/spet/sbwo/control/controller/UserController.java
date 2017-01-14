package spet.sbwo.control.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.control.channel.UserHomeTilesChannel.HomeTile;
import spet.sbwo.control.mapper.UserFavouriteMapper;
import spet.sbwo.control.util.ILoginProvider;
import spet.sbwo.control.util.PasswordHasher;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.WhereOperator;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserFavourite;
import spet.sbwo.data.table.UserHomeTile;
import spet.sbwo.data.table.UserPreference;
import spet.sbwo.data.view.UserPlain;

public class UserController extends BaseController implements ILoginProvider {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private static final String INITIAL_PASSWORD = "init";

	private PasswordHasher hasher;

	public UserController(DatabaseFacade database) {
		super(database);
		this.hasher = new PasswordHasher();
	}

	@Override
	public boolean userExists(String username) {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			return user != null && user.isActive();
		} catch (Exception e) {
			LOG.error("Forcing failed authentication because of underlying error (when checking user existance).");
			return false;
		}
	}

	@Override
	public boolean passwordMatches(String username, String password) {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			if (user == null || !user.isActive()) {
				return false;
			} else {
				return this.hasher.checkPassword(password, user.getPassword(), user.getSalt());
			}
		} catch (Exception e) {
			LOG.error("Forcing failed authentication because of underlying error (when checking user password).");
			return false;
		}
	}

	public void registerUser(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			if (user != null) {
				LOG.warn("Attempted to create already existing user ({}).", username);
				throw new ControlException(ControlError.INVALID_PROPERTY_VALUE, UserChannel.class);
			} else {
				PasswordHasher.HashedPasswordInfo pwdInfo = this.hasher.hashPassword(INITIAL_PASSWORD);
				UserPreference preference = new UserPreference();
				user = new User();
				preference.setUser(user);
				user.setPreference(preference);
				user.setUsername(username);
				user.setPassword(pwdInfo.getHash());
				user.setSalt(pwdInfo.getSalt());
				user.setActive(true);
				executor.create(user);
				executor.create(preference);
				executor.commit();
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to register user because of a database error.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public void activateUser(String username, boolean active) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			if (user == null) {
				LOG.warn("User {} not found during activation.", username);
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, UserChannel.class);
			} else {
				user.setActive(active);
				executor.update(user);
				executor.commit();
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to activate user because of a database error.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public void resetUserPassword(String username) throws ControlException {
		this.changeUserPassword(username, INITIAL_PASSWORD);
	}

	public void changeUserPassword(String username, String password) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			if (user == null) {
				LOG.warn("User {} not found during password reset.", username);
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, UserChannel.class);
			} else {
				PasswordHasher.HashedPasswordInfo pwdInfo = this.hasher.hashPassword(password);
				user.setPassword(pwdInfo.getHash());
				user.setSalt(pwdInfo.getSalt());
				executor.update(user);
				executor.commit();
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to change user password because of a database error.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public UserHomeTilesChannel getTiles(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			UserHomeTilesChannel result = new UserHomeTilesChannel();
			if (user != null) {
				for (UserHomeTile tile : user.getHomeTiles()) {
					result.addTile(tile.getName(), tile.getOrder(), tile.isVisible());
				}
			}
			return result;
		} catch (DatabaseException e) {
			LOG.error("Unable to load user tiles.");
			throw new ControlException(e, UserHomeTilesChannel.class);
		}
	}

	public UserHomeTilesChannel updateTiles(UserHomeTilesChannel data, String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			UserHomeTilesChannel result = new UserHomeTilesChannel();
			if (user != null) {
				Map<String, HomeTile> input = data.getTiles();
				for (UserHomeTile tile : user.getHomeTiles()) {
					HomeTile current = input.get(tile.getName());
					if (current != null) {
						result.getTiles().put(tile.getName(), current);
						tile.setOrder(current.getOrder());
						tile.setVisible(current.isVisible());
					} else {
						result.addTile(tile.getName(), tile.getOrder(), tile.isVisible());
					}
				}
				for (Map.Entry<String, HomeTile> entry : input.entrySet()) {
					if (!result.getTiles().containsKey(entry.getKey())) {
						result.getTiles().put(entry.getKey(), entry.getValue());
						UserHomeTile tile = new UserHomeTile();
						tile.setName(entry.getKey());
						tile.setOrder(entry.getValue().getOrder());
						tile.setVisible(entry.getValue().isVisible());
						tile.setUser(user);
						user.getHomeTiles().add(tile);
						executor.create(tile);
					}
				}
				executor.commit();
			}
			return result;
		} catch (DatabaseException e) {
			LOG.error("Unable to update user tiles.");
			throw new ControlException(e, UserHomeTilesChannel.class);
		}
	}

	public List<UserFavouriteChannel> readFavourites(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			if (user != null) {
				return new UserFavouriteMapper(executor, user).toExternal(user.getFavourites());
			} else {
				return new LinkedList<>();
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to read user favourites.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public void deleteFavourite(String username, int id) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			UserFavourite favourite = executor.find(UserFavourite.class, id);
			if (favourite != null && user != null) {
				user.getFavourites().remove(favourite);
				executor.delete(favourite);
				executor.commit();
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to delete user favourite.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public void addFavourite(String username, UserFavouriteChannel data) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			if (user != null) {
				UserFavourite favourite = new UserFavouriteMapper(executor, user).toInternal(data);
				user.getFavourites().add(favourite);
				executor.create(favourite);
				executor.commit();
				data.setId(favourite.getId());
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to add favourite.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public List<UserFavouriteChannel> updateFavourites(String username, List<UserFavouriteChannel> input)
			throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			User user = this.getUserByUsername(username, executor);
			if (user != null) {
				UserFavouriteMapper mapper = new UserFavouriteMapper(executor, user);
				user.setFavourites(mapper.merge(user.getFavourites(), input));
				mapper.flush();
				executor.commit();
				return mapper.toExternal(user.getFavourites());
			} else {
				return new LinkedList<>();
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to update user favourites.");
			throw new ControlException(e, UserFavouriteChannel.class);
		}
	}

	public User getUserByUsername(String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			return executor.selectSingle(User.class).where("username", WhereOperator.EQ, username).execute();
		} catch (DatabaseException e) {
			LOG.error("Unable to get user by username.");
			throw new ControlException(e, UserChannel.class);
		}
	}

	public List<UserPlain> listAllPlains() throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			return executor.select(UserPlain.class).execute();
		} catch (DatabaseException e) {
			LOG.error("Unable to read all users.");
			throw new ControlException(e, UserChannel.class);
		}
	}
}
