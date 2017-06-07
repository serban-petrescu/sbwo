package spet.sbwo.control.importer.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spet.sbwo.control.importer.base.BaseListImporter;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Location;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LocationImporter extends BaseListImporter<Location> {
    private final LocationProvider provider;

    public LocationImporter(IDatabaseExecutor executor) {
        this.provider = new LocationProvider(executor);
    }

    @Override
    protected Location build(Map<String, String> entry) {
        Location result = new Location();
        result.setAddress(entry.get("location_address"));

        provider.country(entry.get("location_country_code")).ifPresent(c -> {
            result.setCountry(c);
            provider.region(c, entry.get("location_region_code")).ifPresent(r -> {
                result.setRegion(r);
                provider.admUnit(r, entry.get("location_adm_unit_code")).ifPresent(result::setAdministrativeUnit);
            });
        });
        return result;
    }

    public static List<String> fields() {
        return Arrays.asList("location_country_code", "location_region_code", "location_adm_unit_code",
            "location_address");
    }

}
