package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;

import java.time.Duration;

public class DeleteExpertise extends DeleteEntity<Expertise> {

    public DeleteExpertise() {
        super(Expertise.class, ExpertiseChannel.class);
    }

    public DeleteExpertise(Duration directDeleteInterval) {
        super(Expertise.class, ExpertiseChannel.class, directDeleteInterval);
    }

    @Override
    protected void delete(IDatabaseExecutor executor, Expertise t) {
        super.delete(executor, t);
        if (t.getLocation() != null) {
            executor.delete(t.getLocation());
        }
    }
}
