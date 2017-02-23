package spet.sbwo.data.view;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EXPERTISE_SEARCH")
public class ExpertiseSearch {

	@Id
	@Column(name = "C_ID")
	private int id;

	@Column(name = "C_NUMBER")
	private String number;

	@Column(name = "C_YEAR")
	private int year;

	@Column(name = "C_NEXT_HEARING")
	private Date nextHearing;

	@Column(name = "C_TITLE")
	private String title;

	@Column(name = "C_RESPONSIBLE")
	private int responsible;

	@Column(name = "C_COURT")
	private String court;

	@Column(name = "C_STATUS")
	private int status;

	@Column(name = "C_SEARCH")
	private String search;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getResponsible() {
		return responsible;
	}

	public void setResponsible(int responsible) {
		this.responsible = responsible;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getNextHearing() {
		return nextHearing;
	}

	public void setNextHearing(Date nextHearing) {
		this.nextHearing = nextHearing;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
