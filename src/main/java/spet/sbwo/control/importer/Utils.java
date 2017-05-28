package spet.sbwo.control.importer;

import java.math.BigDecimal;

public class Utils {

    private Utils() {
        super();
    }

    public static boolean toBoolean(String input) {
        return input != null && ("yes".equalsIgnoreCase(input) || "true".equalsIgnoreCase(input));
    }

    public static double toDouble(String input) {
        return input != null && !input.isEmpty() ? Double.parseDouble(input) : 0;
    }

    public static <T extends Enum<T>> T toEnum(Class<T> clazz, String input) {
        return input != null && !input.isEmpty() ? Enum.valueOf(clazz, input) : null;
    }

    public static BigDecimal toDecimal(String input) {
        return input != null && !input.isEmpty() ? new BigDecimal(input) : null;
    }
}
