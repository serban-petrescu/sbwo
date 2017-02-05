package spet.sbwo.control.controller.user;

import java.util.Map;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.control.channel.UserHomeTilesChannel.HomeTile;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserHomeTile;

public class TileController extends BaseController {
	private static final String READ_ERROR = "Unable to load user tiles.";
	private static final String UPDATE_ERROR = "Unable to update user tiles.";

	public TileController(IDatabaseExecutorCreator database) {
		super(database, UserHomeTilesChannel.class);
	}

	public UserHomeTilesChannel readTiles(String username) throws ControlException {
		IUserAction<UserHomeTilesChannel> action = (executor, user) -> {
			UserHomeTilesChannel result = new UserHomeTilesChannel();
			for (UserHomeTile tile : user.getHomeTiles()) {
				result.addTile(tile.getName(), tile.getOrder(), tile.isVisible());
			}
			return result;
		};
		return execute(username, READ_ERROR, action);
	}

	public UserHomeTilesChannel updateTiles(UserHomeTilesChannel data, String username) throws ControlException {
		IUserAction<UserHomeTilesChannel> action = (executor, user) -> {
			UserHomeTilesChannel result = new UserHomeTilesChannel();
			updateExisting(user, result, data.getTiles());
			createNew(executor, user, result, data.getTiles());
			executor.commit();
			return result;
		};
		return execute(username, UPDATE_ERROR, action);
	}

	private void createNew(IDatabaseExecutor executor, User user, UserHomeTilesChannel result,
			Map<String, HomeTile> input) throws DatabaseException {
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
	}

	protected void updateExisting(User user, UserHomeTilesChannel result, Map<String, HomeTile> input) {
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
	}

}
