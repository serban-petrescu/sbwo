package spet.sbwo.api.service.util;

public class JsonpEntity<T> {
	private final T entity;
	private final String callback;

	public JsonpEntity(T entity, String callback) {
		this.entity = entity;
		this.callback = callback;
	}

	public T getEntity() {
		return entity;
	}

	public String getCallback() {
		return callback;
	}

}
