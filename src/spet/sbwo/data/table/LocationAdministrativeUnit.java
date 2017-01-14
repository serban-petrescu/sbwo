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
@Table(name = "T_LOCATION_ADM_UNIT", indexes = { @Index(unique = true, columnList = "C_REGION_ID,C_NAME"),
		@Index(unique = true, columnList = "C_REGION_ID,C_CODE") })
public class LocationAdministrativeUnit extends BaseEntity implements ICodifiedEntity<String> {
	@ManyToOne(optional = false)
	@JoinColumn(name = "C_REGION_ID", nullable = false)
	private LocationRegion region;

	@Column(name = "C_NAME", length = 128)
	private String name;

	@Column(name = "C_CODE", length = 16)
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
		return "LocationAdministrativeUnit [region=" + region + ", name=" + name + ", code=" + code + ", id=" + id
				+ "]";
	}

}
