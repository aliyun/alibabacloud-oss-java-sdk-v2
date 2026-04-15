package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.utils.StringUtils;

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


    public static <T> T paramNotNull(final T object, final String paramName) {
        if (object == null) {
            throw new NullPointerException(String.format("%s must not be null.", paramName));
        }
        return object;
    }


    public static <T extends CharSequence> T paramNotBlank(final T chars, final String paramName) {
        if (chars == null) {
            throw new NullPointerException(String.format("%s must not be null.", paramName));
        }
        if (StringUtils.isBlank(chars)) {
            throw new IllegalArgumentException(String.format("%s must not be blank or empty.", paramName));
        }
        return chars;
    }
}