package spet.sbwo.control.importer;

import spet.sbwo.control.importer.base.BaseImporter;
import spet.sbwo.data.base.BaseEntity;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

public class Utils {

    private Utils() {
        super();
    }

    public static boolean toBoolean(String input) {
        return input != null && ("yes".equalsIgnoreCase(input) || "true".equalsIgnoreCase(input));
    }

    public static int toInteger(String input) {
        return input != null && !input.isEmpty() ? Integer.parseInt(input) : 0;
    }

    public static <T extends Enum<T>> T toEnum(Class<T> clazz, String input) {
        return input != null && !input.isEmpty() ? Enum.valueOf(clazz, input) : null;
    }

    public static BigDecimal toDecimal(String input) {
        return input != null && !input.isEmpty() ? new BigDecimal(input) : null;
    }

    public static LocalDate toLocalDate(String input) {
        return input != null && !input.isEmpty() ? LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    public static <T extends BaseEntity> void processImporter(Iterator<Map<String, String>> data,
                                                            BaseImporter<T> importer) {
        if (importer != null) {
            while (data.hasNext()) {
                importer.process(data.next());
            }
        }
    }

    public static <T extends JournalizedBaseEntity> void processImporter(Iterator<Map<String, String>> data, User user,
                                                                        BaseImporter<T> importer) {
        while (data.hasNext()) {
            T current = importer.process(data.next());
            current.setCreatedBy(user);
            current.setCreatedOn(LocalDateTime.now());
        }
    }
}
