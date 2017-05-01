package spet.sbwo.integration.api.court.model;

import java.time.LocalDate;

public class Hearing {
	private LocalDate date;
	private String time;
	private String result;
	private String summary;
	private LocalDate delivery;
	private Document document;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public LocalDate getDelivery() {
		return delivery;
	}

	public void setDelivery(LocalDate delivery) {
		this.delivery = delivery;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
