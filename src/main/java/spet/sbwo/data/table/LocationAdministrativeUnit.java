package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;
import spet.sbwo.data.base.ICodifiedEntity;

@Entity
@Table(name = "T_LOCATION_ADM_UNIT")
public class LocationAdministrativeUnit extends BaseEntity implements ICodifiedEntity<String> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "C_REGION_ID")
    private LocationRegion region;

    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_CODE")
    private String code;

    public LocationRegion getRegion() {
        return region;
    }

    public void setRegion(LocationRegion region) {
        this.region = region;
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

    @Override
    public String toString() {
        return "LocationAdministrativeUnit [name=" + name + ", code=" + code + ", id=" + id + "]";
    }

}
