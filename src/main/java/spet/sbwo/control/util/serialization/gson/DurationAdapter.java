package spet.sbwo.control.util.serialization.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;

class DurationAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {

    @Override
    public Duration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        return json.isJsonNull() ? null : Duration.parse(json.getAsString());
    }

    @Override
    public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toString());
    }
}
