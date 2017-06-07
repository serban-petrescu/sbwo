package spet.sbwo.control.mapper.expertise;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.expertise.ExpertiseFineChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.Utils;
import spet.sbwo.data.table.ExpertiseFine;

class ExpertiseFineMapper implements IMapper<ExpertiseFine, ExpertiseFineChannel> {
    private static final ModelMapper mapper = new ModelMapper();
    static {
        mapper.addMappings(new PropertyMap<ExpertiseFineChannel, ExpertiseFine>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                when(Utils.NOT_NULL).map(source.getDate(), destination.getDate());
                when(Utils.NOT_NULL).map(source.getSum(), destination.getSum());
            }
        });
    }

    ExpertiseFineMapper() {
        super();
    }

    @Override
    public ExpertiseFineChannel toChannel(ExpertiseFine entity) {
        return mapper.map(entity, ExpertiseFineChannel.class);
    }

    @Override
    public ExpertiseFine toEntity(ExpertiseFineChannel channel) {
        return mapper.map(channel, ExpertiseFine.class);
    }

    @Override
    public void mergeIntoEntity(ExpertiseFineChannel channel, ExpertiseFine entity) {
        mapper.map(channel, entity);
    }
}
