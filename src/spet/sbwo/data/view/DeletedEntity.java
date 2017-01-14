package spet.sbwo.data.view;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import spet.sbwo.data.embed.DeletedEntityKey;

@Entity
@Table(name = "V_DELETED")
public class DeletedEntity {

	@EmbeddedId
	private DeletedEntityKey key;

	@Column(name = "C_TITLE")
	private String title;

	@Column(name = "C_DELETEDON")
	private Timestamp deletedOn;

	@Column(name = "C_SEARCH")
	private String search;

	public DeletedEntityKey getKey() {
		return key;
	}

	public void setKey(DeletedEntityKey key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(Timestamp deletedOn) {
		this.deletedOn = deletedOn;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
