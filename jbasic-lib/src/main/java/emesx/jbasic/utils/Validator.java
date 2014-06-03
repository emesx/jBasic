package emesx.jbasic.utils;

import java.util.function.Supplier;

public abstract class Validator {
    public static void forbid(Supplier<Boolean> supplier, String message) {
        if (supplier.get())
            throw new IllegalArgumentException(message);
    }

    public static void require(Supplier<Boolean> supplier, String message) {
        forbid(() -> !supplier.get(), message);
    }

    public static void requireNonNull(Object object, String message) {
        forbid(() -> object == null, message);
    }
}
