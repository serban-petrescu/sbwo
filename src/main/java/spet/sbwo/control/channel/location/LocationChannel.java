package spet.sbwo.control.channel.location;

import spet.sbwo.control.channel.base.BaseChannel;

public class LocationChannel extends BaseChannel {
    private LocationCountryChannel country;
    private LocationRegionChannel region;
    private LocationAdmUnitChannel administrativeUnit;
    private String address;
    private Double latitude;
    private Double longitude;
    private Boolean geocoded;

    public LocationCountryChannel getCountry() {
        return country;
    }

    public void setCountry(LocationCountryChannel country) {
        this.country = country;
    }

    public LocationRegionChannel getRegion() {
        return region;
    }

    public void setRegion(LocationRegionChannel region) {
        this.region = region;
    }

    public LocationAdmUnitChannel getAdministrativeUnit() {
        return administrativeUnit;
    }

    public void setAdministrativeUnit(LocationAdmUnitChannel administrativeUnit) {
        this.administrativeUnit = administrativeUnit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean isGeocoded() {
        return geocoded;
    }

    public void setGeocoded(Boolean geocoded) {
        this.geocoded = geocoded;
    }

}
