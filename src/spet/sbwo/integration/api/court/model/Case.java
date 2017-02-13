package spet.sbwo.integration.api.court.model;

import java.util.List;

public class Case {
	private String number;
	private Long date;
	private String matter;
	private String status;
	private String category;
	private List<Side> sides;
	private List<Hearing> hearings;

	public List<Side> getSides() {
		return sides;
	}

	public void setSides(List<Side> sides) {
		this.sides = sides;
	}

	public List<Hearing> getHearings() {
		return hearings;
	}

	public void setHearings(List<Hearing> hearings) {
		this.hearings = hearings;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getMatter() {
		return matter;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
