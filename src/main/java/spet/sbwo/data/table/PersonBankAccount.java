package spet.sbwo.data.table;

import spet.sbwo.data.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_PERSON_BANK_ACCOUNT")
public class PersonBankAccount extends BaseEntity {
    @Column(name = "C_PRIMARY")
    private boolean primary;

    @Column(name = "C_BANK")
    private String bank;

    @Column(name = "C_ACCOUNT_NUMBER")
    private String accountNumber;

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
        return "PersonBankAccount [primary=" + primary + ", bank=" + bank + ", accountNumber="
            + accountNumber + ", id=" + id + "]";
    }

}
