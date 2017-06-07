package spet.sbwo.data.table;

import java.util.List;

import javax.persistence.*;

import spet.sbwo.data.base.BaseEntity;

@Entity
@Table(name = "T_USER")
public class User extends BaseEntity {
    @Column(name = "C_USERNAME")
    private String username;

    @Column(name = "C_PASSWORD")
    private String password;

    @Column(name = "C_SALT")
    private String salt;

    @Column(name = "C_ACTIVE")
    private boolean active;

    @Column(name = "C_FAILS")
    private int fails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_PREFERENCE_ID")
    private UserPreference preference;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_USER_ID")
    private List<UserHomeTile> homeTiles;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "C_USER_ID")
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

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", salt=" + salt + ", active=" + active
            + ", id=" + id + "]";
    }

}
