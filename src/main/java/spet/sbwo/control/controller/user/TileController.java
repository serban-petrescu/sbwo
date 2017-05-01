package spet.sbwo.control.controller.user;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.ReadTiles;
import spet.sbwo.control.action.user.UpdateTiles;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class TileController extends BaseActionExecutor {

	public TileController(IDatabaseExecutorCreator database) {
		super(database);
	}

	public UserHomeTilesChannel readTiles(String username) throws ControlException {
		return execute(username, new ReadTiles(), null);
	}

	public UserHomeTilesChannel updateTiles(UserHomeTilesChannel data, String username) throws ControlException {
		return executeAndCommit(username, new UpdateTiles(), data);
	}

}
