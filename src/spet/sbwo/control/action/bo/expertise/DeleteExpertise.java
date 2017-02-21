package spet.sbwo.control.action.bo.expertise;

import java.time.Duration;

import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.table.Expertise;

public class DeleteExpertise extends DeleteEntity<Expertise> {

	public DeleteExpertise() {
		super(Expertise.class, ExpertiseChannel.class);
	}

	public DeleteExpertise(Duration directDeleteInterval) {
		super(Expertise.class, ExpertiseChannel.class, directDeleteInterval);
	}
}
