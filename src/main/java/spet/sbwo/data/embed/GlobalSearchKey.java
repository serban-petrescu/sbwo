package spet.sbwo.data.embed;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import spet.sbwo.data.domain.EntityType;

@Embeddable
public class GlobalSearchKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "C_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private EntityType type;

    @Column(name = "C_SUBTYPE")
    private int subtype;

    @Column(name = "C_ID")
    private int id;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + subtype;
        result = prime * result + type.ordinal();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GlobalSearchKey other = (GlobalSearchKey) obj;
        if (id != other.id)
            return false;
        if (subtype != other.subtype)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

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
}
