package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.UserHomeTilesChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserHomeTile;

public class ReadTilesAction extends BaseUserDatabaseAction<Void, UserHomeTilesChannel> {

	public ReadTilesAction() {
		super(UserHomeTilesChannel.class, true);
	}

	@Override
	public UserHomeTilesChannel doRun(Void input, IDatabaseExecutor executor, User user) throws DatabaseException {
		UserHomeTilesChannel result = new UserHomeTilesChannel();
		for (UserHomeTile tile : user.getHomeTiles()) {
			result.addTile(tile.getName(), tile.getOrder(), tile.isVisible());
		}
		return result;
	}

}
