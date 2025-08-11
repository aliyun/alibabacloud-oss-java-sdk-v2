package com.aliyun.sdk.service.oss2.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public final class MapUtils {
    public static Map<String, String> caseInsensitiveMap() {
        return new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public static Map<String, String> caseSensitiveMap() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> of(K k1, V v1) {
        Map<K, V> m = new HashMap<>();
        m.put(k1, v1);
        return m;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        Map<K, V> m = new HashMap<>();
        m.put(k1, v1);
        m.put(k2, v2);
        return m;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> m = new HashMap<>();
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        return m;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> m = new HashMap<>();
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        return m;
    }
}
