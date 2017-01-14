package spet.sbwo.data.table;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_USER")
public class User extends BaseEntity {
	@Column(name = "C_USERNAME", unique = true, length = 30)
	private String username;

	@Column(name = "C_PASSWORD", length = 1024)
	private String password;

	@Column(name = "C_SALT", length = 64)
	private String salt;

	@Column(name = "C_ACTIVE")
	private boolean active;

	@OneToOne(mappedBy = "user")
	private UserPreference preference;

	@OneToMany(mappedBy = "user")
	private List<UserHomeTile> homeTiles;

	@OneToMany(mappedBy = "user")
	private List<UserFavourite> favourites;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public UserPreference getPreference() {
		return preference;
	}

	public void setPreference(UserPreference preference) {
		this.preference = preference;
	}

	public List<UserHomeTile> getHomeTiles() {
		return homeTiles;
	}

	public void setHomeTiles(List<UserHomeTile> homeTiles) {
		this.homeTiles = homeTiles;
	}

	public List<UserFavourite> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<UserFavourite> favourites) {
		this.favourites = favourites;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", salt=" + salt + ", active=" + active
				+ ", preference=" + preference + ", homeTiles=" + homeTiles + ", favourites=" + favourites + ", id="
				+ id + "]";
	}

}
