package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_USER_HOME_TILE")
public class UserHomeTile extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "C_USER_ID")
    private User user;

    @Column(name = "C_NAME", length = 32)
    private String name;

    @Column(name = "C_ORDER")
    private int order;

    @Column(name = "C_VISIBLE")
    private boolean visible;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "UserHomeTile [user=" + user + ", name=" + name + ", order=" + order + ", visible=" + visible + ", id="
            + id + "]";
    }

}
