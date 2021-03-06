package spet.sbwo.data.view;

import spet.sbwo.data.domain.EntityType;
import spet.sbwo.data.embed.DeletedEntityKey;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Cacheable(false)
@IdClass(DeletedEntityKey.class)
@Table(name = "V_DELETED")
public class DeletedEntity {

    @Id
    @Column(name = "C_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private EntityType type;

    @Id
    @Column(name = "C_ID")
    private int id;

    @Column(name = "C_TITLE")
    private String title;

    @Column(name = "C_DELETEDON")
    private Timestamp deletedOn;

    @Column(name = "C_SEARCH")
    private String search;

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
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

    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
