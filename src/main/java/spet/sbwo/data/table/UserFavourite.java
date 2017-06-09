package spet.sbwo.data.table;

import spet.sbwo.data.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER_FAVOURITE")
public class UserFavourite extends BaseEntity {
    @Column(name = "C_TITLE")
    private String title;

    @Column(name = "C_HASH")
    private String hash;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "UserFavourite [title=" + title + ", hash=" + hash + ", id=" + id + "]";
    }
}
