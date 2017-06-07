package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.UpdateEntity;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.expertise.ExpertiseMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;

public class UpdateExpertise extends UpdateEntity<Expertise, ExpertiseChannel> {

    public UpdateExpertise() {
        super(Expertise.class, ExpertiseChannel.class);
    }

    @Override
    protected IMapper<Expertise, ExpertiseChannel> mapper(IDatabaseExecutor executor) {
        return ExpertiseMapper.newInstance(executor);
    }

}
