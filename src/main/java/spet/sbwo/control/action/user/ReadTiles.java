package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserHomeTilesChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserHomeTile;

public class ReadTiles extends BaseUserDatabaseAction<Void, UserHomeTilesChannel> {

    public ReadTiles() {
        super(UserHomeTilesChannel.class, true);
    }

    @Override
    public UserHomeTilesChannel doRun(Void input, IDatabaseExecutor executor, User user) {
        UserHomeTilesChannel result = new UserHomeTilesChannel();
        for (UserHomeTile tile : user.getHomeTiles()) {
            result.addTile(tile.getName(), tile.getOrder(), tile.isVisible());
        }
        return result;
    }

}
