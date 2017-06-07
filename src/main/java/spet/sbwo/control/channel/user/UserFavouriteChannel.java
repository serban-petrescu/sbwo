package spet.sbwo.control.channel.user;

import spet.sbwo.control.channel.base.BaseChannel;

public class UserFavouriteChannel extends BaseChannel {
    private String hash;
    private String title;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
