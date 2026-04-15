package com.aliyun.sdk.service.oss2.tables.models.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CastUtils {
    private CastUtils() {
    }

    public static <T> List<T> ensureList(List<T> value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return value;
    }

    public static <K, V> Map<K, V> ensureMap(Map<K, V> value) {
        if (value == null) {
            return Collections.emptyMap();
        }
        return value;
    }
}
