package spet.sbwo.data.view;

import spet.sbwo.data.domain.EntityType;
import spet.sbwo.data.embed.GlobalSearchKey;

import javax.persistence.*;

@Entity
@Cacheable(false)
@IdClass(GlobalSearchKey.class)
@Table(name = "V_GLOBAL_SEARCH")
public class GlobalSearch {
    @Id
    @Column(name = "C_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private EntityType type;

    @Id
    @Column(name = "C_SUBTYPE")
    private int subtype;

    @Id
    @Column(name = "C_ID")
    private int id;

    @Column(name = "C_TITLE")
    private String title;

    @Column(name = "C_DESCRIPTION")
    private String description;

    @Column(name = "C_SEARCH")
    private String search;

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public int getSubtype() {
        return subtype;
    }

    public void setSubtype(int subtype) {
        this.subtype = subtype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
