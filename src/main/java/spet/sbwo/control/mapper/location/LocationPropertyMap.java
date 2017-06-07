package spet.sbwo.control.mapper.location;

import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.location.LocationChannel;
import spet.sbwo.control.mapper.Utils;
import spet.sbwo.data.table.Location;

class LocationPropertyMap extends PropertyMap<LocationChannel, Location> {
    @Override
    protected void configure() {
        skip(destination.getId());
        skip(destination.getAdministrativeUnit());
        skip(destination.getRegion());
        skip(destination.getCountry());
        skip(destination.getLatitude());
        skip(destination.getLongitude());
        when(Utils.NOT_NULL).map(source.getAddress(), destination.getAddress());
        when(Utils.NOT_NULL).map(source.isGeocoded(), destination.isGeocoded());
    }
}
