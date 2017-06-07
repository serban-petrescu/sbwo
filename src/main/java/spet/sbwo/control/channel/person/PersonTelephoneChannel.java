package spet.sbwo.control.channel.person;

import spet.sbwo.control.channel.base.BaseChannel;

public class PersonTelephoneChannel extends BaseChannel {
    private String telephone;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
