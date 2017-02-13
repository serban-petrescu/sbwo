package spet.sbwo.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

@Provider
@Produces("application/json")
@Consumes("application/json")
public class JsonProducer implements MessageBodyWriter<Object>, MessageBodyReader<Object> {
	private static final String UTF_8 = "UTF-8";

	private final Gson gson;

	public JsonProducer() {
		this.gson = new Gson();
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
		return gson.fromJson(new InputStreamReader(entityStream, UTF_8), genericType);
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type != JsonFragmentStream.class;
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
		gson.toJson(t, genericType, writer);
		writer.flush();
	}

}
