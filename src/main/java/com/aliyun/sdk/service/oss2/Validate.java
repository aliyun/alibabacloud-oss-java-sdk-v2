package com.aliyun.sdk.service.oss2;

public final class Validate {
    private static final String DEFAULT_NULL_EX_MESSAGE = "The object is validated as null";

    private Validate() {
    }

    public static void isTrue(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T> T notNull(T object, String message, Object... values) {
        if (object == null) {
            throw new NullPointerException(String.format(message, values));
        } else {
            return object;
        }
    }

    public static <T> void isNull(T object, String message, Object... values) {
        if (object != null) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T> T paramNotNull(T object, String paramName) {
        if (object == null) {
            throw new NullPointerException(String.format("%s must not be null.", paramName));
        } else {
            return object;
        }
    }
}