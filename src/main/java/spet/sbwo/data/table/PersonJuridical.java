package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "1")
public class PersonJuridical extends Person {
    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_ID_NUMBER")
    private String idNumber;

    @Column(name = "C_REG_NUMBER")
    private String regNumber;

    @Column(name = "C_JOINT_STOCK")
    private BigDecimal jointStock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public BigDecimal getJointStock() {
        return jointStock;
    }

    public void setJointStock(BigDecimal jointStock) {
        this.jointStock = jointStock;
    }

    @Override
    public String toString() {
        return "PersonJuridical [name=" + name + ", idNumber=" + idNumber + ", regNumber=" + regNumber + ", jointStock="
            + jointStock + ", id=" + id + "]";
    }

}
