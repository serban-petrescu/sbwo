package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_PERSON_EMAIL_ADDRESS", indexes = { @Index(unique = true, columnList = "C_PERSON_ID,C_NAME") })
public class PersonEmailAddress extends BaseEntity {
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "C_PERSON_ID")
	private Person person;

	@Column(name = "C_NAME", length = 64)
	private String name;

	@Column(name = "C_PRIMARY")
	private boolean primary;

	@Column(name = "C_EMAIL", length = 128)
	private String email;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

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
		return "PersonEmailAddress [person=" + person + ", name=" + name + ", primary=" + primary + ", email=" + email
				+ ", id=" + id + "]";
	}
	
}
