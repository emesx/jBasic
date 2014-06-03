package emesx.jbasic.utils;

public abstract class Utils {
    public static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    public static Object[] arr(Object... objects) {
        return objects;
    }
}
