package spet.sbwo.control.action.misc;

import java.util.List;

import spet.sbwo.control.action.base.BaseUserDatabaseAction;
import spet.sbwo.control.action.bo.base.RestoreEntity;
import spet.sbwo.control.action.bo.expertise.RestoreExpertise;
import spet.sbwo.control.action.bo.person.RestorePerson;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.EntityType;
import spet.sbwo.data.table.User;

public class RestoreList extends BaseUserDatabaseAction<List<TrashChannel>, Void> {

    public RestoreList() {
        super(TrashChannel.class, true);
    }

    @Override
    public Void doRun(List<TrashChannel> input, IDatabaseExecutor executor, User user) {
        for (TrashChannel c : input) {
            getActionFor(c.getType()).run(c.getId(), executor, user);
        }
        return null;
    }

    protected RestoreEntity<?> getActionFor(EntityType type) {
        if (type == EntityType.PERSON) {
            return new RestorePerson();
        } else {
            return new RestoreExpertise();
        }
    }

}
