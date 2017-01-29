package spet.sbwo.control.helper;

import java.util.Map;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.control.channel.UserHomeTilesChannel.HomeTile;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserHomeTile;

public class UserTileHelper extends BaseController {

	public UserHomeTilesChannel getTiles(IDatabaseExecutor executor, String username) throws ControlException {
		User user = this.getUserByUsername(executor, username);
		UserHomeTilesChannel result = new UserHomeTilesChannel();
		for (UserHomeTile tile : user.getHomeTiles()) {
			result.addTile(tile.getName(), tile.getOrder(), tile.isVisible());
		}
		return result;
	}

	public UserHomeTilesChannel updateTiles(IDatabaseExecutor executor, UserHomeTilesChannel data, String username)
			throws ControlException, DatabaseException {
		User user = this.getUserByUsername(executor, username);
		UserHomeTilesChannel result = new UserHomeTilesChannel();
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
		return result;
	}
}
