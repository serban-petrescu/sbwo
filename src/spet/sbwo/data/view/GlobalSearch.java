package spet.sbwo.data.view;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import spet.sbwo.data.embed.GlobalSearchKey;

@Entity
@Table(name = "V_GLOBAL_SEARCH")
public class GlobalSearch {
	@EmbeddedId
	private GlobalSearchKey key;

	@Column(name = "C_TITLE")
	private String title;

	@Column(name = "C_DESCRIPTION")
	private String description;

	@Column(name = "C_SEARCH")
	private String search;

	public GlobalSearchKey getKey() {
		return key;
	}

	public void setKey(GlobalSearchKey key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
