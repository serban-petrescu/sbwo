package spet.sbwo.control.channel.user;

import spet.sbwo.control.channel.base.BaseChannel;

public class UserChannel extends BaseChannel {
    private String username;
    private Boolean active;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
