package com.aliyun.sdk.service.oss2.utils;

import java.time.Instant;

public final class ConvertUtils {
    private ConvertUtils() {
    }

    public static Integer toIntegerOrNull(final String str) {
        if (str == null) {
            return null;
        }
        return Integer.valueOf(str);
    }

    public static Long toLongOrNull(final String str) {
        if (str == null) {
            return null;
        }
        return Long.valueOf(str);
    }

    public static Boolean toBoolOrNull(final String str) {
        if (str == null) {
            return null;
        }
        return Boolean.valueOf(str);
    }

    public static Double toDoubleOrNull(final String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static Instant toInstantOrNull(final String str) {
        if (str == null) {
            return null;
        }
        return Instant.parse(str);
    }
}
