package com.aliyun.sdk.service.oss2.models.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CastUtils {

    public static <T> List<T> ensureList(List<T> value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return value;
    }

    public static Map<String, String> toMetadata(Map<String, String> value) {
        if (value == null) {
            return  null;
        }

        return value.entrySet().stream()
                .filter(x -> x.getKey().toLowerCase().startsWith("x-oss-meta-"))
                .collect(Collectors.toMap(
                        x-> x.getKey().substring(11),
                        Map.Entry::getValue,
                        (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
                        () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER))
                );
    }

    private CastUtils() {

    }
}
