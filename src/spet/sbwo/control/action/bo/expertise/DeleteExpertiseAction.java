package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.DeleteEntityAction;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.table.Expertise;

public class DeleteExpertiseAction extends DeleteEntityAction<Expertise>{

	public DeleteExpertiseAction() {
		super(Expertise.class, ExpertiseChannel.class);
	}

	public DeleteExpertiseAction(int directDeleteInterval) {
		super(Expertise.class, ExpertiseChannel.class, directDeleteInterval);
	}
}
