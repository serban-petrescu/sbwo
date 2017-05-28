package spet.sbwo.control.controller.user;

import java.util.List;

import spet.sbwo.control.action.base.BaseActionExecutor;
import spet.sbwo.control.action.user.AddFavourite;
import spet.sbwo.control.action.user.DeleteFavourite;
import spet.sbwo.control.action.user.ReadFavourites;
import spet.sbwo.control.action.user.UpdateFavourites;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class FavouriteController extends BaseActionExecutor {

    public FavouriteController(IDatabaseExecutorCreator database) {
        super(database);
    }

    public List<UserFavouriteChannel> readFavourites(String username) {
        return execute(username, new ReadFavourites(), null);
    }

    public void deleteFavourite(String username, int id) {
        executeAndCommit(username, new DeleteFavourite(), id);
    }

    public void addFavourite(String username, UserFavouriteChannel data) {
        executeAndCommit(username, new AddFavourite(), data);
    }

    public List<UserFavouriteChannel> updateFavourites(String username, List<UserFavouriteChannel> input) {
        return executeAndCommit(username, new UpdateFavourites(), input);
    }

}
