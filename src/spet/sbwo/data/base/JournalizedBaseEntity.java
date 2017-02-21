package spet.sbwo.data.base;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import spet.sbwo.data.table.User;

@MappedSuperclass
public class JournalizedBaseEntity extends BaseEntity {
	@ManyToOne(optional = true)
	@JoinColumn(name = "C_CREATEDBY_ID", nullable = true)
	protected User createdBy;

	@Column(name = "C_CREATEDON")
	protected LocalDateTime createdOn;

	@ManyToOne(optional = true)
	@JoinColumn(name = "C_CHANGEDBY_ID", nullable = true)
	protected User changedBy;

	@Column(name = "C_CHANGEDON")
	protected LocalDateTime changedOn;

	@Column(name = "C_DELETED", nullable = false)
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
