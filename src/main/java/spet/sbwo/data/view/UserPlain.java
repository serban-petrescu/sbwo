package spet.sbwo.data.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "V_USER_PLAIN")
public class UserPlain extends BaseEntity {
    @Column(name = "C_USERNAME")
    private String username;

    @Column(name = "C_ACTIVE")
    private boolean active;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
