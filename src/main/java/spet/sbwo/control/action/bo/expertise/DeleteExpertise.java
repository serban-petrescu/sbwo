package spet.sbwo.control.action.bo.expertise;

import java.time.Duration;

import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;

public class DeleteExpertise extends DeleteEntity<Expertise> {

	public DeleteExpertise() {
		super(Expertise.class, ExpertiseChannel.class);
	}

	public DeleteExpertise(Duration directDeleteInterval) {
		super(Expertise.class, ExpertiseChannel.class, directDeleteInterval);
	}

	@Override
	protected void delete(IDatabaseExecutor executor, Expertise t) throws DatabaseException {
		super.delete(executor, t);
		if (t.getLocation() != null) {
			executor.delete(t.getLocation());
		}
	}
}
