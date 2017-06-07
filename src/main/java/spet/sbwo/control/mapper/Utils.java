package spet.sbwo.control.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import spet.sbwo.control.channel.base.BaseChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.BaseEntity;

import java.util.function.Function;

public class Utils {

    private Utils() {
        super();
    }

    public static Condition<?, ?> NOT_NULL = c -> c.getSource() != null;

    public static <T> T retrieveDependent(BaseChannel channel, Function<Integer, T> provider) {
        if (channel != null && channel.getId() != null) {
            return provider.apply(channel.getId());
        } else {
            return null;
        }
    }

    public static <T extends BaseEntity> Function<Integer, T> provider(IDatabaseExecutor executor, Class<T> clazz) {
        return id -> executor.find(clazz, id);
    }

    public static class ToEnumConverter<T extends Enum<T>> extends AbstractConverter<Integer, T> {
        final Class<T> clazz;

        public ToEnumConverter(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        protected T convert(Integer integer) {
            return clazz.getEnumConstants()[integer];
        }
    }

    public static class FromEnumConverter<T extends Enum<T>> extends AbstractConverter<T, Integer> {
        @Override
        protected Integer convert(T value) {
            return value.ordinal();
        }
    }

}
