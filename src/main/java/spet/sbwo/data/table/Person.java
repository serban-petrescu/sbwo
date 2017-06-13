package spet.sbwo.data.table;

import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.domain.PersonType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_PERSON")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "C_PERSON_TYPE", discriminatorType = DiscriminatorType.INTEGER)
public class Person extends JournalizedBaseEntity {
    @Column(name = "C_PERSON_TYPE", insertable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private PersonType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_LOCATION_ID")
    private Location location;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_PERSON_ID")
    private List<PersonBankAccount> bankAccounts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_PERSON_ID")
    private List<PersonTelephone> telephones;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_PERSON_ID")
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
