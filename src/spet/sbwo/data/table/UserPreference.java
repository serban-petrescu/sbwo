package spet.sbwo.data.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_USER_PREFERENCE")
public class UserPreference extends BaseEntity {

	@OneToOne(optional = false)
	@JoinColumn(name = "C_USER_ID")
	private User user;

	@Column(name = "C_LANGUAGE", length = 16)
	private String language;

	@Column(name = "C_THEME", length = 32)
	private String theme;

	@Column(name = "C_DRAFT_RESUME_DELAY")
	private int draftResumeDelay;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getDraftResumeDelay() {
		return draftResumeDelay;
	}

	public void setDraftResumeDelay(int draftResumeDelay) {
		this.draftResumeDelay = draftResumeDelay;
	}

	@Override
	public String toString() {
		return "UserPreference [id=" + id + ", user=" + user + ", language=" + language + ", theme=" + theme
				+ ", draftResumeDelay=" + draftResumeDelay + "]";
	}

}
