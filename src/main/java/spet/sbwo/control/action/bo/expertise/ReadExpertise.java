package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.ReadEntity;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.expertise.ExpertiseMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;

public class ReadExpertise extends ReadEntity<Expertise, ExpertiseChannel> {

    public ReadExpertise() {
        super(Expertise.class, ExpertiseChannel.class);
    }

    @Override
    protected IMapper<Expertise, ExpertiseChannel> mapper(IDatabaseExecutor executor) {
        return ExpertiseMapper.newInstance(executor);
    }

}
