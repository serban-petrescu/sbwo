package spet.sbwo.control.channel.location;

import spet.sbwo.control.channel.base.BaseChannel;

public class LocationCountryChannel extends BaseChannel {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
