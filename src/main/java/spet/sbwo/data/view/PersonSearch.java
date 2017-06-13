package spet.sbwo.data.view;

import spet.sbwo.data.base.BaseEntity;
import spet.sbwo.data.domain.PersonType;

import javax.persistence.*;

@Entity
@Cacheable(false)
@Table(name = "V_PERSON_SEARCH")
public class PersonSearch extends BaseEntity {
    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private PersonType type;

    @Column(name = "C_COUNTRY")
    private String country;

    @Column(name = "C_REGION")
    private String region;

    @Column(name = "C_ADDRESS")
    private String address;

    @Column(name = "C_SEARCH")
    private String search;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
