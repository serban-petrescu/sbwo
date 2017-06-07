package spet.sbwo.control.mapper.expertise;

import org.modelmapper.ModelMapper;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.Utils;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.ExpertiseStatus;
import spet.sbwo.data.table.Court;
import spet.sbwo.data.table.Expertise;
import spet.sbwo.data.table.User;

import java.util.function.Function;

public class ExpertiseMapper implements IMapper<Expertise, ExpertiseChannel> {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.addMappings(new ExpertiseToEntityPropertyMap());
        mapper.addMappings(new ExpertiseToChannelPropertyMap());
        mapper.addConverter(new Utils.ToEnumConverter<ExpertiseStatus>(ExpertiseStatus.class) {
        });
        mapper.addConverter(new Utils.FromEnumConverter<ExpertiseStatus>() {
        });
    }

    private final Function<Integer, User> userProvider;
    private final Function<Integer, Court> courtProvider;
    private final ExpertiseFineMapper fineMapper;

    ExpertiseMapper(Function<Integer, User> userProvider,
                           Function<Integer, Court> courtProvider) {
        this.userProvider = userProvider;
        this.courtProvider = courtProvider;
        this.fineMapper = new ExpertiseFineMapper();
    }

    public static IMapper<Expertise, ExpertiseChannel> newInstance(IDatabaseExecutor executor) {
        Function<Integer, User> userProvider = Utils.provider(executor, User.class);
        Function<Integer, Court> courtProvider = Utils.provider(executor, Court.class);
        return new ExpertiseMapper(userProvider, courtProvider);
    }


    @Override
    public ExpertiseChannel toChannel(Expertise entity) {
        return mapper.map(entity, ExpertiseChannel.class);
    }

    @Override
    public Expertise toEntity(ExpertiseChannel channel) {
        Expertise entity = mapper.map(channel, Expertise.class);
        entity.setResponsible(Utils.retrieveDependent(channel.getResponsible(), userProvider));
        entity.setCourt(Utils.retrieveDependent(channel.getCourt(), courtProvider));
        entity.setFines(fineMapper.toEntities(channel.getFines()));
        return entity;
    }

    @Override
    public void mergeIntoEntity(ExpertiseChannel channel, Expertise entity) {
        mapper.map(channel, entity);
        entity.setResponsible(Utils.retrieveDependent(channel.getResponsible(), userProvider));
        entity.setCourt(Utils.retrieveDependent(channel.getCourt(), courtProvider));
        entity.setFines(fineMapper.toEntities(channel.getFines()));
    }

}
