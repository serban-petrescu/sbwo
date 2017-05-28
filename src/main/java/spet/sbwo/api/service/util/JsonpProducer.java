package spet.sbwo.api.service.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.api.service.base.IPublic;

@Provider
@Produces("application/javascript")
@SuppressWarnings("rawtypes")
public class JsonpProducer implements MessageBodyWriter<JsonpEntity>, IPublic, IPrivate {
    private static final String UTF_8 = "UTF-8";

    private final Gson gson;

    public JsonpProducer() {
        this.gson = new Gson();
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == JsonpEntity.class;
    }

    @Override
    public long getSize(JsonpEntity t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(JsonpEntity t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        Writer writer = new OutputStreamWriter(entityStream, UTF_8);
        writer.write(t.getCallback());
        writer.write("(");
        gson.toJson(t.getEntity(), t.getEntity().getClass(), writer);
        writer.write(")");
        writer.flush();
    }

}
