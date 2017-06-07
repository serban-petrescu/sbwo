package spet.sbwo.control.mapper;

import spet.sbwo.control.channel.base.BaseChannel;
import spet.sbwo.data.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private TestUtils() {
        super();
    }

    public static <E extends BaseEntity> List<E> mockEntities(Class<E> clazz, int... ids) {
        List<E> result = new ArrayList<>();
        for (int id : ids) {
            result.add(mockEntity(clazz, id));
        }
        return result;
    }

    public static <C extends BaseChannel> List<C> mockChannels(Class<C> clazz, int... ids) {
        List<C> result = new ArrayList<>();
        for (int id : ids) {
            result.add(mockChannel(clazz, id));
        }
        return result;
    }

    public static <E extends BaseEntity> E mockEntity(Class<E> clazz, int id) {
        try {
            E e = clazz.newInstance();
            e.setId(id);
            return e;
        } catch (Exception e) {
            throw new AssertionError("Unable to mock entity.", e);
        }
    }

    public static <C extends BaseChannel> C mockChannel(Class<C> clazz, int id) {
        try {
            C c = clazz.newInstance();
            c.setId(id);
            return c;
        } catch (Exception e) {
            throw new AssertionError("Unable to mock channel.", e);
        }
    }

}
