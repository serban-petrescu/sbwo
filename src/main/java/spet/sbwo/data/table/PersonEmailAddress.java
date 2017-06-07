package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_PERSON_EMAIL_ADDRESS")
public class PersonEmailAddress extends BaseEntity {
    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_PRIMARY")
    private boolean primary;

    @Column(name = "C_EMAIL")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonEmailAddress [name=" + name + ", primary=" + primary + ", email=" + email + ", id=" + id + "]";
    }

}
