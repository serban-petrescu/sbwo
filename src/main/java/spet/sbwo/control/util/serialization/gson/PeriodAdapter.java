package spet.sbwo.control.util.serialization.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Period;

class PeriodAdapter implements JsonSerializer<Period>, JsonDeserializer<Period> {

    @Override
    public Period deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        return json.isJsonNull() ? null : Period.parse(json.getAsString());
    }

    @Override
    public JsonElement serialize(Period src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toString());
    }
}
