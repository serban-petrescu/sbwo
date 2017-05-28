package spet.sbwo.data.table;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;
import spet.sbwo.data.base.ICodifiedEntity;

@Entity
@Table(name = "T_LOCATION_REGION", indexes = {@Index(unique = true, columnList = "C_COUNTRY_ID,C_NAME"),
    @Index(unique = true, columnList = "C_COUNTRY_ID,C_CODE")})
public class LocationRegion extends BaseEntity implements ICodifiedEntity<String> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "C_COUNTRY_ID", nullable = false)
    private LocationCountry country;

    @Column(name = "C_NAME", length = 128)
    private String name;

    @Column(name = "C_CODE", length = 16)
    private String code;

    @OneToMany(mappedBy = "region")
    private List<LocationAdministrativeUnit> administrativeUnits;

    public LocationCountry getCountry() {
        return country;
    }

    public void setCountry(LocationCountry country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<LocationAdministrativeUnit> getAdministrativeUnits() {
        return administrativeUnits;
    }

    public void setAdministrativeUnits(List<LocationAdministrativeUnit> administrativeUnits) {
        this.administrativeUnits = administrativeUnits;
    }

    @Override
    public String toString() {
        return "LocationRegion [country=" + country + ", name=" + name + ", code=" + code + ", administrativeUnits="
            + administrativeUnits + ", id=" + id + "]";
    }

}
