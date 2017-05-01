package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.UpdateEntity;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.control.mapper.BaseMapper;
import spet.sbwo.control.mapper.ExpertiseMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;

public class UpdateExpertise extends UpdateEntity<Expertise, ExpertiseChannel> {

	public UpdateExpertise() {
		super(Expertise.class, ExpertiseChannel.class);
	}

	@Override
	protected BaseMapper<Expertise, ExpertiseChannel> mapper(IDatabaseExecutor executor) {
		return new ExpertiseMapper(executor);
	}

}
