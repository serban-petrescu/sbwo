package spet.sbwo.control.channel.person;

import spet.sbwo.control.channel.base.JournalChannel;
import spet.sbwo.control.channel.location.LocationChannel;

import java.math.BigDecimal;
import java.util.List;

public class PersonChannel extends JournalChannel {
    private Integer type;
    private LocationChannel location;
    private List<PersonBankAccountChannel> bankAccounts;
    private List<PersonTelephoneChannel> telephones;
    private List<PersonEmailChannel> emailAddresses;
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

    public List<PersonBankAccountChannel> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<PersonBankAccountChannel> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<PersonTelephoneChannel> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<PersonTelephoneChannel> telephones) {
        this.telephones = telephones;
    }

    public List<PersonEmailChannel> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<PersonEmailChannel> emailAddresses) {
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

}
