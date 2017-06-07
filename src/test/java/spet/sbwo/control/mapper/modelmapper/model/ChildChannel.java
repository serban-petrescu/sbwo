package spet.sbwo.control.mapper.modelmapper.model;

public class ChildChannel {
    private Integer id;
    private Integer name;

    public static ChildChannel of(int id, Integer name) {
        ChildChannel result = new ChildChannel();
        result.setId(id);
        result.setName(name);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }


}
