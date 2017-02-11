package spet.sbwo.control.action.user;

import java.util.List;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.view.UserPlain;

public class ReadAllPlainsAction extends BaseDatabaseAction<Void, List<UserPlain>> {

	public ReadAllPlainsAction() {
		super(UserChannel.class);
	}

	@Override
	public List<UserPlain> doRun(Void input, IDatabaseExecutor e) throws DatabaseException {
		return e.select(UserPlain.class).execute();
	}

}
