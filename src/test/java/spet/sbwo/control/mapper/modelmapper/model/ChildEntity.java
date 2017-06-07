package spet.sbwo.control.mapper.modelmapper.model;

public class ChildEntity {
    private int id;
    private SomeEnum name;
    private ParentEntity parent;

    public static ChildEntity of(int id, SomeEnum name, ParentEntity parent) {
        ChildEntity result = new ChildEntity();
        result.setId(id);
        result.setName(name);
        result.setParent(parent);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SomeEnum getName() {
        return name;
    }

    public void setName(SomeEnum name) {
        this.name = name;
    }

    public ParentEntity getParent() {
        return parent;
    }

    public void setParent(ParentEntity parent) {
        this.parent = parent;
    }
}
