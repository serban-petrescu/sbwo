package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_PERSON_BANK_ACCOUNT", indexes = { @Index(unique = true, columnList = "C_PERSON_ID,C_ACCOUNT_NUMBER") })
public class PersonBankAccount extends BaseEntity {
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "C_PERSON_ID")
	private Person person;

	@Column(name = "C_PRIMARY")
	private boolean primary;

	@Column(name = "C_BANK", length = 128)
	private String bank;

	@Column(name = "C_ACCOUNT_NUMBER", length = 64)
	private String accountNumber;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "PersonBankAccount [person=" + person + ", primary=" + primary + ", bank=" + bank + ", accountNumber="
				+ accountNumber + ", id=" + id + "]";
	}

}
