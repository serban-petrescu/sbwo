package spet.sbwo.control.channel;

import java.math.BigDecimal;
import java.util.List;

public class PersonChannel extends JournalChannel {
	private Integer type;
	private LocationChannel location;
	private List<BankAccount> bankAccounts;
	private List<Telephone> telephones;
	private List<Email> emailAddresses;
	private String name;
	private String idNumber;
	private String regNumber;
	private String firstName;
	private String lastName;
	private String personalNumber;
	private Integer identityCardType;
	private String identityCardNumber;
	private BigDecimal jointStock;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public LocationChannel getLocation() {
		return location;
	}

	public void setLocation(LocationChannel location) {
		this.location = location;
	}

	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public List<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public List<Email> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(List<Email> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	public Integer getIdentityCardType() {
		return identityCardType;
	}

	public void setIdentityCardType(Integer identityCardType) {
		this.identityCardType = identityCardType;
	}

	public String getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}

	public BigDecimal getJointStock() {
		return jointStock;
	}

	public void setJointStock(BigDecimal jointStock) {
		this.jointStock = jointStock;
	}

	public static class Child extends BaseChannel {
		private String name;
		private Boolean primary;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Boolean isPrimary() {
			return primary;
		}

		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}
	}

	public static class BankAccount extends BaseChannel {
		private Boolean primary;
		private String bank;
		private String accountNumber;

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

		public Boolean isPrimary() {
			return primary;
		}

		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}
	}

	public static class Telephone extends Child {
		private String telephone;

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

	}

	public static class Email extends Child {
		private String email;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}
}
