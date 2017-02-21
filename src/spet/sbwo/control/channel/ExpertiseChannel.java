package spet.sbwo.control.channel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExpertiseChannel extends JournalChannel {
	private String number;
	private CourtChannel court;
	private int year;
	private String title;
	private Integer status;
	private String note;
	private UserChannel responsible;
	private LocalDateTime lastCheckedOn;
	private LocalDate nextHearing;
	private BigDecimal price;
	private BigDecimal advance;
	private List<Fine> fines;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAdvance() {
		return advance;
	}

	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}

	public List<Fine> getFines() {
		return fines;
	}

	public void setFines(List<Fine> fines) {
		this.fines = fines;
	}

	public static class Fine extends BaseChannel {
		private LocalDate date;
		private BigDecimal sum;

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public BigDecimal getSum() {
			return sum;
		}

		public void setSum(BigDecimal sum) {
			this.sum = sum;
		}

	}

}
