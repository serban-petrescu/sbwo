package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.CreateEntity;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.expertise.ExpertiseMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;

public class CreateExpertise extends CreateEntity<Expertise, ExpertiseChannel> {

    public CreateExpertise() {
        super(Expertise.class, ExpertiseChannel.class);
    }

    @Override
    protected IMapper<Expertise, ExpertiseChannel> mapper(IDatabaseExecutor executor) {
        return ExpertiseMapper.newInstance(executor);
    }

}
