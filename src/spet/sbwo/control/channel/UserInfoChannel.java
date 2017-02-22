package spet.sbwo.control.channel;

import java.util.List;

public class UserInfoChannel extends BaseChannel {
	private String username;
	private UserPreferenceChannel preference;
	private List<UserChannel> others;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserPreferenceChannel getPreference() {
		return preference;
	}

	public void setPreference(UserPreferenceChannel preference) {
		this.preference = preference;
	}

	public List<UserChannel> getOthers() {
		return others;
	}

	public void setOthers(List<UserChannel> others) {
		this.others = others;
	}

}
