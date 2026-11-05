package com.aliyun.sdk.service.oss2.internal.serde;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Captures a generic type, e.g. {@code new TypeRef<List<String>>() {}}.
 * Used instead of Jackson's TypeReference so that callers do not depend on Jackson types.
 */
public abstract class TypeRef<T> {
    private final Type type;

    protected TypeRef() {
        Type superClass = getClass().getGenericSuperclass();
        if (!(superClass instanceof ParameterizedType)) {
            throw new IllegalArgumentException("TypeRef must be created with actual type information");
        }
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
