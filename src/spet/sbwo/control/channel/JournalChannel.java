package spet.sbwo.control.channel;

public class JournalChannel extends BaseChannel{
	private UserChannel createdBy;
	private Long createdOn;
	private UserChannel changedBy;
	private Long changedOn;
	private Boolean deleted;

	public UserChannel getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserChannel createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}

	public UserChannel getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(UserChannel changedBy) {
		this.changedBy = changedBy;
	}

	public Long getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(Long changedOn) {
		this.changedOn = changedOn;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
