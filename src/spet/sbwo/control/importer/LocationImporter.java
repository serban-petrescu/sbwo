package spet.sbwo.control.importer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.query.WhereOperator;
import spet.sbwo.data.table.Location;
import spet.sbwo.data.table.LocationAdministrativeUnit;
import spet.sbwo.data.table.LocationCountry;
import spet.sbwo.data.table.LocationRegion;

class LocationImporter extends BaseListImporter<Location> {
	private LocationProvider provider;

	public LocationImporter(IDatabaseExecutor executor) {
		this.provider = new LocationProvider(executor);
	}

	@Override
	protected Location build(Map<String, String> entry) throws DatabaseException {
		Location result = new Location();
		result.setAddress(entry.get("location_address"));

		LocationCountry country = this.provider.country(entry.get("location_country_code"));
		if (country != null) {
			result.setCountry(country);
			LocationRegion region = this.provider.region(country, entry.get("location_region_code"));
			if (region != null) {
				result.setRegion(region);
				result.setAdministrativeUnit(this.provider.admUnit(region, entry.get("location_adm_unit_code")));
			}
		}

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

		public LocationCountry country(String code) throws DatabaseException {
			return executor.selectSingle(LocationCountry.class).where("code", WhereOperator.EQ, code).execute();
		}

		public LocationRegion region(LocationCountry country, String region) throws DatabaseException {
			return executor.selectSingle(LocationRegion.class).where("country", WhereOperator.EQ, country)
					.where("code", WhereOperator.EQ, region).execute();
		}

		public LocationAdministrativeUnit admUnit(LocationRegion region, String admUnit) throws DatabaseException {
			return executor.selectSingle(LocationAdministrativeUnit.class).where("region", WhereOperator.EQ, region)
					.where("code", WhereOperator.EQ, admUnit).execute();
		}
	}
}
