package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.RestoreEntityAction;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.table.Expertise;

public class RestoreExpertiseAction extends RestoreEntityAction<Expertise>{

	public RestoreExpertiseAction() {
		super(Expertise.class, ExpertiseChannel.class);
	}

}
