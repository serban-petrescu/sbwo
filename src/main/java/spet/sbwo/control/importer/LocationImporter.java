package spet.sbwo.control.importer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.importer.base.BaseListImporter;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Location;
import spet.sbwo.data.table.LocationAdministrativeUnit;
import spet.sbwo.data.table.LocationCountry;
import spet.sbwo.data.table.LocationRegion;

public class LocationImporter extends BaseListImporter<Location> {
	private static final Logger LOG = LoggerFactory.getLogger(LocationImporter.class);
	private LocationProvider provider;

	public LocationImporter(IDatabaseExecutor executor) {
		this.provider = new LocationProvider(executor);
	}

	@Override
	protected Location build(Map<String, String> entry)  {
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

	public class LocationProvider {
		private IDatabaseExecutor executor;

		public LocationProvider(IDatabaseExecutor executor) {
			this.executor = executor;
		}

		public Optional<LocationCountry> country(String code)  {
			return executor.querySingle("LocationCountry.getByCode", LocationCountry.class, code);
		}

		public Optional<LocationRegion> region(LocationCountry country, String region) {
			try {
				return executor.querySingle("LocationRegion.getByCountryAndCode", LocationRegion.class, country,
						region);
			} catch (DatabaseException e) {
				LOG.error("Error while reading region.", e);
				return Optional.empty();
			}
		}

		public Optional<LocationAdministrativeUnit> admUnit(LocationRegion region, String admUnit) {
			try {
				return executor.querySingle("LocationAdministrativeUnit.getByRegionAndCode",
						LocationAdministrativeUnit.class, region, admUnit);
			} catch (DatabaseException e) {
				LOG.error("Error while reading administrative unit.", e);
				return Optional.empty();
			}
		}
	}
}
