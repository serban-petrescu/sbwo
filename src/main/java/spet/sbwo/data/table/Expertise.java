package spet.sbwo.data.table;

import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.domain.ExpertiseStatus;
import spet.sbwo.data.embed.Tariff;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "T_EXPERTISE")
public class Expertise extends JournalizedBaseEntity {

    @Column(name = "C_NUMBER")
    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "C_COURT_ID")
    private Court court;

    @Column(name = "C_YEAR")
    private int year;

    @Column(name = "C_TITLE")
    private String title;

    @Column(name = "C_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private ExpertiseStatus status;

    @Column(name = "C_NOTE")
    private String note;

    @ManyToOne
    @JoinColumn(name = "C_USER_ID")
    private User responsible;

    @Column(name = "C_LAST_CHECKED_ON")
    private LocalDateTime lastCheckedOn;

    @Column(name = "C_NEXT_HEARING")
    private LocalDate nextHearing;

    @Embedded
    private Tariff tariff;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_LOCATION_ID")
    private Location location;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_EXPERTISE_ID")
    private List<ExpertiseFine> fines;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExpertiseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpertiseStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public LocalDateTime getLastCheckedOn() {
        return lastCheckedOn;
    }

    public void setLastCheckedOn(LocalDateTime lastCheckedOn) {
        this.lastCheckedOn = lastCheckedOn;
    }

    public LocalDate getNextHearing() {
        return nextHearing;
    }

    public void setNextHearing(LocalDate nextHearing) {
        this.nextHearing = nextHearing;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public List<ExpertiseFine> getFines() {
        return fines;
    }

    public void setFines(List<ExpertiseFine> fines) {
        this.fines = fines;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Expertise [id=" + id + ", number=" + number + ", title=" + title + ", responsible=" + responsible + "]";
    }

}
