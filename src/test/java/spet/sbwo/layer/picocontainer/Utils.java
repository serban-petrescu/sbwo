package spet.sbwo.layer.picocontainer;

import java.util.Arrays;
import java.util.Collection;

class Utils {

    public static <E> boolean collectionContainsInstanceOf(Collection<E> collection, Class<? extends E> clazz) {
        for (E element : collection) {
            if (clazz.isInstance(element)) {
                return true;
            }
        }
        return false;
    }

    public static <E> boolean arrayContainsInstanceOf(E[] array, Class<? extends E> clazz) {
        return collectionContainsInstanceOf(Arrays.asList(array), clazz);
    }

    public static <E> boolean arrayContains(E[] array, E element) {
        return Arrays.asList(array).indexOf(element) >= 0;
    }
}
