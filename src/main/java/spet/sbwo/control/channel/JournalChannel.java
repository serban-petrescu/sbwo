package spet.sbwo.control.channel;

import java.time.LocalDateTime;

public class JournalChannel extends BaseChannel {
    private UserChannel createdBy;
    private LocalDateTime createdOn;
    private UserChannel changedBy;
    private LocalDateTime changedOn;
    private Boolean deleted;

    public UserChannel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserChannel createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public UserChannel getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(UserChannel changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedOn() {
        return changedOn;
    }

    public void setChangedOn(LocalDateTime changedOn) {
        this.changedOn = changedOn;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
