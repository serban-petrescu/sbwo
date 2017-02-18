package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.ReadEntity;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.control.mapper.BaseMapper;
import spet.sbwo.control.mapper.ExpertiseMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;

public class ReadExpertise extends ReadEntity<Expertise, ExpertiseChannel> {

	public ReadExpertise() {
		super(Expertise.class, ExpertiseChannel.class);
	}

	@Override
	protected BaseMapper<Expertise, ExpertiseChannel> mapper(IDatabaseExecutor executor) {
		return new ExpertiseMapper(executor);
	}

}
