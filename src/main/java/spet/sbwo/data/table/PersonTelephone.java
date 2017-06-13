package spet.sbwo.data.table;

import spet.sbwo.data.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_PERSON_TELEPHONE")
public class PersonTelephone extends BaseEntity {
    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_PRIMARY")
    private boolean primary;

    @Column(name = "C_TELEPHONE")
    private String telephone;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "PersonTelephone [name=" + name + ", primary=" + primary + ", telephone=" + telephone + ", id=" + id +
            "]";
    }

}

