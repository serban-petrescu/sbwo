package spet.sbwo.control.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonSerializationHelper implements ISerializationHelper {
	private final Gson gson;

	public GsonSerializationHelper() {
		gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(Duration.class, new DurationAdapter())
				.registerTypeAdapter(Period.class, new PeriodAdapter())
				.registerTypeAdapter(File.class, new FileAdapter()).create();
	}

	@Override
	public void serialize(Object data, Type type, Writer writer) throws IOException {
		gson.toJson(data, type, writer);
	}

	@Override
	public <T> T deserialize(Type type, Reader reader) throws IOException {
		return gson.fromJson(reader, type);
	}

	private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
		private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

		@Override
		public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return json.isJsonNull() ? null : LocalDate.from(FORMATTER.parse(json.getAsString()));
		}

		@Override
		public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? JsonNull.INSTANCE : new JsonPrimitive(FORMATTER.format(src));
		}
	}

	private static class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
		private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

		@Override
		public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return json.isJsonNull() ? null : LocalTime.from(FORMATTER.parse(json.getAsString()));
		}

		@Override
		public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? JsonNull.INSTANCE : new JsonPrimitive(FORMATTER.format(src));
		}
	}

	private static class LocalDateTimeAdapter
			implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
		private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		@Override
		public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return json.isJsonNull() ? null : LocalDateTime.from(FORMATTER.parse(json.getAsString()));
		}

		@Override
		public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? JsonNull.INSTANCE : new JsonPrimitive(FORMATTER.format(src));
		}
	}

	private static class DurationAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {

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

	private static class PeriodAdapter implements JsonSerializer<Period>, JsonDeserializer<Period> {

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

	private static class FileAdapter implements JsonSerializer<File>, JsonDeserializer<File> {

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
}
