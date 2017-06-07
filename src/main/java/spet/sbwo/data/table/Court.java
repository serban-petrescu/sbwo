package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_COURT")
public class Court extends BaseEntity {
    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_CODE")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Court [id=" + id + ", name=" + name + ", code=" + code + "]";
    }

}
