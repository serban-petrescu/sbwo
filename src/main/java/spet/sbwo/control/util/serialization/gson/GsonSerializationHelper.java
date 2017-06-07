package spet.sbwo.control.util.serialization.gson;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spet.sbwo.control.util.serialization.ISerializationHelper;

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
}
