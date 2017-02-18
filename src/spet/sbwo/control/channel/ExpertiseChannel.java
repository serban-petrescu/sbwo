package spet.sbwo.control.channel;

public class ExpertiseChannel extends JournalChannel {
	private String number;
	private CourtChannel court;
	private int year;
	private String title;
	private Integer status;
	private String note;
	private UserChannel responsible;
	private Long lastCheckedOn;
	private Long nextHearing;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public CourtChannel getCourt() {
		return court;
	}

	public void setCourt(CourtChannel court) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public UserChannel getResponsible() {
		return responsible;
	}

	public void setResponsible(UserChannel responsible) {
		this.responsible = responsible;
	}

	public Long getLastCheckedOn() {
		return lastCheckedOn;
	}

	public void setLastCheckedOn(Long lastCheckedOn) {
		this.lastCheckedOn = lastCheckedOn;
	}

	public Long getNextHearing() {
		return nextHearing;
	}

	public void setNextHearing(Long nextHearing) {
		this.nextHearing = nextHearing;
	}

}
