package spet.sbwo.control.action.misc;

import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.misc.TrashChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.view.DeletedEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ReadAllTrash extends BaseDatabaseAction<Void, List<TrashChannel>> {

    public ReadAllTrash() {
        super(TrashChannel.class);
    }

    @Override
    public List<TrashChannel> doRun(Void input, IDatabaseExecutor executor) {
        List<DeletedEntity> entities = executor.queryList("DeletedEntity.readAll", DeletedEntity.class);
        return entities.stream().map(entity -> {
            TrashChannel channel = new TrashChannel();
            channel.setId(entity.getId());
            channel.setType(entity.getType());
            return channel;
        }).collect(Collectors.toList());
    }

}
