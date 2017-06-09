package spet.sbwo.data.table;

import spet.sbwo.data.domain.IdentityCardType;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "0")
public class PersonNatural extends Person {
    @Column(name = "C_FIRST_NAME")
    private String firstName;

    @Column(name = "C_LAST_NAME")
    private String lastName;

    @Column(name = "C_PERSONAL_NUMBER")
    private String personalNumber;

    @Column(name = "C_ID_CARD_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private IdentityCardType identityCardType;

    @Column(name = "C_ID_CARD_NUMBER")
    private String identityCardNumber;

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

    public IdentityCardType getIdentityCardType() {
        return identityCardType;
    }

    public void setIdentityCardType(IdentityCardType identityCardType) {
        this.identityCardType = identityCardType;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    @Override
    public String toString() {
        return "PersonNatural [firstName=" + firstName + ", lastName=" + lastName + ", personalNumber=" + personalNumber
            + ", identityCardType=" + identityCardType + ", identityCardNumber=" + identityCardNumber + ", id=" + id
            + "]";
    }

}
