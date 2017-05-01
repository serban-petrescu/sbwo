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
	@ManyToOne
	@JoinColumn(name = "C_EXPERTISE_ID")
	private Expertise expertise;

	@Column(name = "C_DATE")
	private LocalDate date;

	@Column(name = "C_SUM", nullable = true, precision = 16, scale = 2)
	private BigDecimal sum;
	
	public Expertise getExpertise() {
		return expertise;
	}

	public void setExpertise(Expertise expertise) {
		this.expertise = expertise;
	}

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

}
