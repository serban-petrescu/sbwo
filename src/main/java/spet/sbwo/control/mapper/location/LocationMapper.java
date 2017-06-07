package spet.sbwo.control.mapper.location;

import org.modelmapper.ModelMapper;
import spet.sbwo.control.channel.location.LocationChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.Utils;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Location;
import spet.sbwo.data.table.LocationAdministrativeUnit;
import spet.sbwo.data.table.LocationCountry;
import spet.sbwo.data.table.LocationRegion;

import java.util.function.Function;

import static spet.sbwo.control.mapper.Utils.provider;

public class LocationMapper implements IMapper<Location, LocationChannel> {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.addMappings(new LocationPropertyMap());
    }

    private final Function<Integer, LocationCountry> countryProvider;
    private final Function<Integer, LocationRegion> regionProvider;
    private final Function<Integer, LocationAdministrativeUnit> admUnitProvider;

    LocationMapper(Function<Integer, LocationCountry> countryProvider,
                          Function<Integer, LocationRegion> regionProvider,
                          Function<Integer, LocationAdministrativeUnit> admUnitProvider) {
        this.countryProvider = countryProvider;
        this.regionProvider = regionProvider;
        this.admUnitProvider = admUnitProvider;
    }

    public static IMapper<Location, LocationChannel> newInstance(IDatabaseExecutor executor) {
        return new LocationMapper(provider(executor, LocationCountry.class),
            provider(executor, LocationRegion.class),
            provider(executor, LocationAdministrativeUnit.class));
    }

    @Override
    public LocationChannel toChannel(Location entity) {
        return mapper.map(entity, LocationChannel.class);
    }

    @Override
    public Location toEntity(LocationChannel channel) {
        Location entity = mapper.map(channel, Location.class);
        updateDependents(channel, entity);
        return entity;
    }

    @Override
    public void mergeIntoEntity(LocationChannel channel, Location entity) {
        mapper.map(channel, entity);
        updateDependents(channel, entity);
    }

    private void updateDependents(LocationChannel channel, Location entity) {
        entity.setCountry(Utils.retrieveDependent(channel.getCountry(), countryProvider));
        entity.setRegion(Utils.retrieveDependent(channel.getRegion(), regionProvider));
        entity.setAdministrativeUnit(Utils.retrieveDependent(channel.getAdministrativeUnit(), admUnitProvider));
    }

}
