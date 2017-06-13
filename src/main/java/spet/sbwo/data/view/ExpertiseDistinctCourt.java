package spet.sbwo.data.view;

import javax.persistence.*;

@Entity
@Cacheable(false)
@Table(name = "V_EXPERTISE_DISTINCT_COURT")
public class ExpertiseDistinctCourt {
    @Id
    @Column(name = "C_ID")
    private int id;

    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_CODE")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
