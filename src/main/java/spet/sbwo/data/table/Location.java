package spet.sbwo.data.table;

import spet.sbwo.data.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_LOCATION")
public class Location extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "C_COUNTRY_ID")
    private LocationCountry country;

    @ManyToOne
    @JoinColumn(name = "C_REGION_ID")
    private LocationRegion region;

    @ManyToOne
    @JoinColumn(name = "C_ADM_UNIT_ID")
    private LocationAdministrativeUnit administrativeUnit;

    @Column(name = "C_ADDRESS")
    private String address;

    @Column(name = "C_LATITUDE")
    private Double latitude;

    @Column(name = "C_LONGITUDE")
    private Double longitude;

    @Column(name = "C_GEOCODED")
    private boolean geocoded;

    public LocationCountry getCountry() {
        return country;
    }

    public void setCountry(LocationCountry country) {
        this.country = country;
    }

    public LocationRegion getRegion() {
        return region;
    }

    public void setRegion(LocationRegion region) {
        this.region = region;
    }

    public LocationAdministrativeUnit getAdministrativeUnit() {
        return administrativeUnit;
    }

    public void setAdministrativeUnit(LocationAdministrativeUnit administrativeUnit) {
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

    public boolean isGeocoded() {
        return geocoded;
    }

    public void setGeocoded(boolean geocoded) {
        this.geocoded = geocoded;
    }

    @Override
    public String toString() {
        return "Location [country=" + country + ", region=" + region + ", administrativeUnit=" + administrativeUnit
            + ", address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + ", id=" + id + "]";
    }

}
