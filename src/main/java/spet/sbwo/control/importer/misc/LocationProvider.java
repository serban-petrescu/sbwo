package spet.sbwo.control.importer.misc;

import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.LocationAdministrativeUnit;
import spet.sbwo.data.table.LocationCountry;
import spet.sbwo.data.table.LocationRegion;

import java.util.Optional;

public class LocationProvider {
    private final IDatabaseExecutor executor;

    LocationProvider(IDatabaseExecutor executor) {
        this.executor = executor;
    }

    Optional<LocationCountry> country(String code) {
        return executor.querySingle("LocationCountry.getByCode", LocationCountry.class, code);
    }

    Optional<LocationRegion> region(LocationCountry country, String region) {
        return executor.querySingle("LocationRegion.getByCountryAndCode", LocationRegion.class, country,
            region);
    }

    Optional<LocationAdministrativeUnit> admUnit(LocationRegion region, String admUnit) {
        return executor.querySingle("LocationAdministrativeUnit.getByRegionAndCode",
            LocationAdministrativeUnit.class, region, admUnit);
    }
}
