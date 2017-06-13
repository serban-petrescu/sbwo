package spet.sbwo.data.base;

import spet.sbwo.data.table.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class JournalizedBaseEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "C_CREATEDBY_ID")
    protected User createdBy;

    @Column(name = "C_CREATEDON")
    protected LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "C_CHANGEDBY_ID")
    protected User changedBy;

    @Column(name = "C_CHANGEDON")
    protected LocalDateTime changedOn;

    @Column(name = "C_DELETED")
    protected boolean deleted;


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedOn() {
        return changedOn;
    }

    public void setChangedOn(LocalDateTime changedOn) {
        this.changedOn = changedOn;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
