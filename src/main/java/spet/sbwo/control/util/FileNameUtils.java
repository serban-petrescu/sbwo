package spet.sbwo.control.util;

public class FileNameUtils {
    private FileNameUtils() {
        super();
    }

    public static String extension(String name) {
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return "";
        } else {
            return name.substring(index + 1);
        }
    }

    public static String base(String name) {
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return name;
        } else {
            return name.substring(0, index);
        }
    }
}
