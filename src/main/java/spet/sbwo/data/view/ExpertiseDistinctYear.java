package spet.sbwo.data.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EXPERTISE_DISTINCT_YEAR")
public class ExpertiseDistinctYear {

    @Id
    @Column(name = "C_YEAR")
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
