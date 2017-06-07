package spet.sbwo.control.mapper.modelmapper.model;

import java.util.ArrayList;
import java.util.List;

public class ParentChannel {
    private Integer id;
    private Integer value;
    private String string;
    private List<ChildChannel> children = new ArrayList<>();
    private Integer siblingId;
    private String siblingName;

    public static ParentChannel of(Integer id, Integer value, String string) {
        return of(id, value, string, null, null);
    }

    public static ParentChannel of(Integer id, Integer value, String string, Integer siblingId, String siblingName) {
        ParentChannel result = new ParentChannel();
        result.setId(id);
        result.setValue(value);
        result.setString(string);
        result.setSiblingId(siblingId);
        result.setSiblingName(siblingName);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<ChildChannel> getChildren() {
        return children;
    }

    public void setChildren(List<ChildChannel> children) {
        this.children = children;
    }

    public Integer getSiblingId() {
        return siblingId;
    }

    public void setSiblingId(Integer siblingId) {
        this.siblingId = siblingId;
    }

    public String getSiblingName() {
        return siblingName;
    }

    public void setSiblingName(String siblingName) {
        this.siblingName = siblingName;
    }
}
