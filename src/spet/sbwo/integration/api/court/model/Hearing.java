package spet.sbwo.integration.api.court.model;

public class Hearing {
	private Long date;
	private String time;
	private String result;
	private String summary;
	private Long delivery;
	private Document document;

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
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

	public Long getDelivery() {
		return delivery;
	}

	public void setDelivery(Long delivery) {
		this.delivery = delivery;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
