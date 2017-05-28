package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_USER_FAVOURITE")
public class UserFavourite extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "C_USER_ID")
    private User user;

    @Column(name = "C_TITLE", length = 64)
    private String title;

    @Column(name = "C_HASH", length = 64)
    private String hash;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
        return "UserFavourite [user=" + user + ", title=" + title + ", hash=" + hash + ", id=" + id + "]";
    }
}
