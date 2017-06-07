package spet.sbwo.control.util.serialization.gson;

import com.google.gson.*;

import java.io.File;
import java.lang.reflect.Type;

class FileAdapter implements JsonSerializer<File>, JsonDeserializer<File> {

    @Override
    public File deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        return json.isJsonNull() ? null : new File(json.getAsString());
    }

    @Override
    public JsonElement serialize(File src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.getAbsolutePath());
    }
}
