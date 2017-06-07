package spet.sbwo.control.mapper.modelmapper.model;

public class SiblingEntity {
    private int id;
    private String name;

    public static SiblingEntity of(int id, String name) {
        SiblingEntity result = new SiblingEntity();
        result.setId(id);
        result.setName(name);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
