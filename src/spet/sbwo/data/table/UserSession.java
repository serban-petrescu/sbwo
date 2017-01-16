package spet.sbwo.data.table;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "T_USER_SESSION")
public class UserSession {

	@Id
	@Column(name = "C_ID", length = 128)
	private String id;

	@Column(name = "C_CONTEXT_PATH", length = 64)
	private String contextPath;

	@Column(name = "C_VIRTUAL_HOST", length = 64)
	private String virtualHost;

	@Column(name = "C_LAST_NODE", length = 64)
	private String lastNode;

	@Column(name = "C_ACCESS_TIME")
	private long accessTime;

	@Column(name = "C_LAST_ACCESS_TIME")
	private long lastAccessTime;

	@Column(name = "C_CREATE_TIME")
	private long createTime;

	@Column(name = "C_COOKIE_TIME")
	private long cookieTime;

	@Column(name = "C_LAST_SAVE_TIME")
	private long lastSaveTime;

	@Column(name = "C_EXPIRY_TIME")
	private long expiryTime;

	@Column(name = "C_MAX_INTERVAL")
	private long maxInterval;
	
	@Column(name = "C_ATTRIBUTES")
	@Type(type = "serializable")
	private HashMap<String, Object> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public String getLastNode() {
		return lastNode;
	}

	public void setLastNode(String lastNode) {
		this.lastNode = lastNode;
	}

	public long getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(long accessTime) {
		this.accessTime = accessTime;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getCookieTime() {
		return cookieTime;
	}

	public void setCookieTime(long cookieTime) {
		this.cookieTime = cookieTime;
	}

	public long getLastSaveTime() {
		return lastSaveTime;
	}

	public void setLastSaveTime(long lastSaveTime) {
		this.lastSaveTime = lastSaveTime;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public long getMaxInterval() {
		return maxInterval;
	}

	public void setMaxInterval(long maxInterval) {
		this.maxInterval = maxInterval;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}
}
