package spet.sbwo.control.channel.person;

import spet.sbwo.control.channel.base.BaseChannel;

public class PersonEmailChannel extends BaseChannel {
    private String email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
