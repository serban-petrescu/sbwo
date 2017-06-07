package spet.sbwo.control.mapper.modelmapper.model;

import java.util.ArrayList;
import java.util.List;

public class ParentEntity {
    private int id;
    private int value;
    private String string;
    private List<ChildEntity> children = new ArrayList<>();
    private SiblingEntity sibling;

    public static ParentEntity of(int id, int value, String string) {
        ParentEntity result = new ParentEntity();
        result.setId(id);
        result.setValue(value);
        result.setString(string);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChildEntity> children) {
        this.children = children;
    }

    public SiblingEntity getSibling() {
        return sibling;
    }

    public void setSibling(SiblingEntity sibling) {
        this.sibling = sibling;
    }
}
