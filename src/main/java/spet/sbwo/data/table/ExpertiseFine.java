package spet.sbwo.data.table;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_EXPERTISE_FINE")
public class ExpertiseFine extends BaseEntity {

    @Column(name = "C_DATE")
    private LocalDate date;

    @Column(name = "C_SUM")
    private BigDecimal sum;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "ExpertiseFine{date=" + date + ", sum=" + sum + '}';
    }
}
