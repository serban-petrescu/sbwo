package spet.sbwo.api.service.util;

import javax.ws.rs.core.Response;

public class JsonpUtils {

	private JsonpUtils() {
		super();
	}

	public static <T> Response response(T entity, String callback) {
		if (callback == null) {
			return Response.ok(entity, "application/json").build();
		} else {
			return Response.ok(new JsonpEntity<>(entity, callback), "application/javascript").build();
		}
	}
}
