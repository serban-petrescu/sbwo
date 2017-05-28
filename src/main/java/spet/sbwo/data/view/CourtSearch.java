package spet.sbwo.data.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_COURT_SEARCH")
public class CourtSearch {

    @Id
    @Column(name = "C_ID")
    private int id;

    @Column(name = "C_CODE")
    private String code;

    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_SEARCH")
    private String search;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
