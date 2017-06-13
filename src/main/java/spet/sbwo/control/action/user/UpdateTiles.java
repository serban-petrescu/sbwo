package spet.sbwo.control.action.user;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.channel.user.UserHomeTilesChannel;
import spet.sbwo.control.channel.user.UserHomeTilesChannel.HomeTile;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserHomeTile;

import java.util.Map;

public class UpdateTiles extends BaseUserDatabaseAction<UserHomeTilesChannel, UserHomeTilesChannel> {

    public UpdateTiles() {
        super(UserHomeTilesChannel.class, true);
    }

    @Override
    public UserHomeTilesChannel doRun(UserHomeTilesChannel input, IDatabaseExecutor executor, User user) {
        UserHomeTilesChannel result = new UserHomeTilesChannel();
        updateExisting(user, result, input.getTiles());
        createNew(executor, user, result, input.getTiles());
        return result;
    }

    protected void createNew(IDatabaseExecutor executor, User user, UserHomeTilesChannel result,
                             Map<String, HomeTile> input) {
        for (Map.Entry<String, HomeTile> entry : input.entrySet()) {
            if (!result.getTiles().containsKey(entry.getKey())) {
                result.getTiles().put(entry.getKey(), entry.getValue());
                UserHomeTile tile = new UserHomeTile();
                tile.setName(entry.getKey());
                tile.setOrder(entry.getValue().getOrder());
                tile.setVisible(entry.getValue().isVisible());
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
