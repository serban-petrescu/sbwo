package spet.sbwo.control.util.serialization;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

public interface ISerializationHelper {

    void serialize(Object data, Type type, Writer writer);

    <T> T deserialize(Type type, Reader reader);

}
