package spet.sbwo.control.mapper;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.LocationChannel;
import spet.sbwo.control.channel.LocationChannel.AdmUnit;
import spet.sbwo.control.channel.LocationChannel.Country;
import spet.sbwo.control.channel.LocationChannel.Region;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Location;
import spet.sbwo.data.table.LocationAdministrativeUnit;
import spet.sbwo.data.table.LocationCountry;
import spet.sbwo.data.table.LocationRegion;

public class LocationMapper extends BaseMapper<Location, LocationChannel> {
	private CountryMapper countryMapper;
	private RegionMapper regionMapper;
	private AdmUnitMapper admUnitMapper;

	public LocationMapper(IDatabaseExecutor executor) {
		super(executor);
		this.countryMapper = new CountryMapper(executor);
		this.regionMapper = new RegionMapper(executor);
		this.admUnitMapper = new AdmUnitMapper(executor);

		this.countryMapper.setReturnNullOnNullId(true);
		this.regionMapper.setReturnNullOnNullId(true);
		this.admUnitMapper.setReturnNullOnNullId(true);
	}

	@Override
	protected Location newInternal(LocationChannel external) {
		return new Location();
	}

	@Override
	protected LocationChannel newExternal(Location internal) {
		return new LocationChannel();
	}

	@Override
	public void merge(LocationChannel external, Location internal) throws ControlException {
		super.merge(external, internal);
		external.setAddress(internal.getAddress());
		external.setAdministrativeUnit(this.admUnitMapper.toExternal(internal.getAdministrativeUnit()));
		external.setCountry(this.countryMapper.toExternal(internal.getCountry()));
		external.setLatitude(internal.getLatitude());
		external.setLongitude(internal.getLongitude());
		external.setRegion(this.regionMapper.toExternal(internal.getRegion()));
		external.setGeocoded(internal.isGeocoded());
	}

	@Override
	public void merge(Location internal, LocationChannel external) throws ControlException {
		super.merge(internal, external);
		internal.setAddress(external.getAddress());
		internal.setAdministrativeUnit(this.admUnitMapper.toInternal(external.getAdministrativeUnit()));
		internal.setCountry(this.countryMapper.toInternal(external.getCountry()));
		internal.setLatitude(external.getLatitude());
		internal.setLongitude(external.getLongitude());
		internal.setRegion(this.regionMapper.toInternal(external.getRegion()));
		ifNotNull(external.getGeocoded(), internal::setGeocoded);
	}

	public Location toInternalMandatory(Location internal, LocationChannel external) throws ControlException {
		if (external != null) {
			if (internal != null) {
				merge(internal, external);
				return internal;
			} else {
				return toInternal(external);
			}
		} else if (internal == null) {
			return new Location();
		}
		return internal;
	}

	public static class CountryMapper extends BaseMapper<LocationCountry, LocationChannel.Country> {
		public CountryMapper(IDatabaseExecutor executor) {
			super(executor);
		}

		@Override
		protected LocationCountry newInternal(LocationChannel.Country external) {
			return new LocationCountry();
		}

		@Override
		protected LocationChannel.Country newExternal(LocationCountry internal) {
			return new LocationChannel.Country();
		}

		@Override
		public LocationChannel.Country toExternal(LocationCountry input) throws ControlException {
			LocationChannel.Country result = this.newExternal(input);
			if (input != null) {
				this.merge(result, input);
			} else {
				result.setId(null);
			}
			return result;
		}

		@Override
		public void merge(Country external, LocationCountry internal) throws ControlException {
			super.merge(external, internal);
			external.setName(internal.getName());
			external.setCode(internal.getCode());
		}

		@Override
		public void merge(LocationCountry internal, LocationChannel.Country external) throws ControlException {
			super.merge(internal, external);
			internal.setName(external.getName());
			internal.setCode(external.getCode());
		}
	}

	public static class RegionMapper extends BaseMapper<LocationRegion, LocationChannel.Region> {
		public RegionMapper(IDatabaseExecutor executor) {
			super(executor);
		}

		@Override
		protected LocationRegion newInternal(LocationChannel.Region external) {
			return new LocationRegion();
		}

		@Override
		protected LocationChannel.Region newExternal(LocationRegion internal) {
			return new LocationChannel.Region();
		}

		@Override
		public LocationChannel.Region toExternal(LocationRegion input) throws ControlException {
			LocationChannel.Region result = this.newExternal(input);
			if (input != null) {
				this.merge(result, input);
			} else {
				result.setId(null);
			}
			return result;
		}

		@Override
		public void merge(Region external, LocationRegion internal) throws ControlException {
			super.merge(external, internal);
			external.setName(internal.getName());
			external.setCode(internal.getCode());
		}

		@Override
		public void merge(LocationRegion internal, LocationChannel.Region external) throws ControlException {
			super.merge(internal, external);
			internal.setName(external.getName());
			internal.setCode(external.getCode());
		}
	}

	public static class AdmUnitMapper extends BaseMapper<LocationAdministrativeUnit, LocationChannel.AdmUnit> {
		public AdmUnitMapper(IDatabaseExecutor executor) {
			super(executor);
		}

		@Override
		protected LocationAdministrativeUnit newInternal(LocationChannel.AdmUnit external) {
			return new LocationAdministrativeUnit();
		}

		@Override
		protected LocationChannel.AdmUnit newExternal(LocationAdministrativeUnit internal) {
			return new LocationChannel.AdmUnit();
		}

		@Override
		public LocationChannel.AdmUnit toExternal(LocationAdministrativeUnit input) throws ControlException {
			LocationChannel.AdmUnit result = this.newExternal(input);
			if (input != null) {
				this.merge(result, input);
			} else {
				result.setId(null);
			}
			return result;
		}

		@Override
		public void merge(AdmUnit external, LocationAdministrativeUnit internal) throws ControlException {
			super.merge(external, internal);
			external.setName(internal.getName());
			external.setCode(internal.getCode());
		}

		@Override
		public void merge(LocationAdministrativeUnit internal, LocationChannel.AdmUnit external)
				throws ControlException {
			super.merge(internal, external);
			internal.setName(external.getName());
			internal.setCode(external.getCode());
		}
	}

}
