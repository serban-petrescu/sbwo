package spet.sbwo.data.table;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.domain.PersonType;

@Entity
@Table(name = "T_PERSON")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "C_PERSON_TYPE", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Person extends JournalizedBaseEntity {
    @Column(name = "C_PERSON_TYPE", insertable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private PersonType type;

    @OneToOne(optional = false)
    @JoinColumn(name = "C_LOCATION_ID", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "person")
    private List<PersonBankAccount> bankAccounts;

    @OneToMany(mappedBy = "person")
    private List<PersonTelephone> telephones;

    @OneToMany(mappedBy = "person")
    private List<PersonEmailAddress> emailAddresses;

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<PersonBankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<PersonBankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<PersonTelephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<PersonTelephone> telephones) {
        this.telephones = telephones;
    }

    public List<PersonEmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<PersonEmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", type=" + type + ", location=" + location + ", bankAccounts=" + bankAccounts
            + ", telephones=" + telephones + ", emailAddresses=" + emailAddresses + "]";
    }

}
