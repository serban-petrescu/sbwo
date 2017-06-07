package spet.sbwo.control.channel.person;

import spet.sbwo.control.channel.base.BaseChannel;

public class PersonBankAccountChannel extends BaseChannel {
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
