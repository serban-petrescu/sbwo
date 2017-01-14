package spet.sbwo.data.table;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserPreference [user=" + user + ", id=" + id + "]";
	}
	
}
