package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.io.StreamObserver;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

public final class AttributeKey<T> extends AttributeMap.Key<T> {

    public static final AttributeKey<List<String>> SUBRESOURCE = new AttributeKey<>(new UnsafeValueType(List.class));
    public static final AttributeKey<List<Consumer<ResponseMessage>>> RESPONSE_HANDLER = new AttributeKey<>(new UnsafeValueType(List.class));
    public static final AttributeKey<Instant> EXPIRATION_TIME = new AttributeKey<>(new UnsafeValueType(Instant.class));
    public static final AttributeKey<List<StreamObserver>> UPLOAD_OBSERVER = new AttributeKey<>(new UnsafeValueType(List.class));
    public static final AttributeKey<Boolean> RESPONSE_HEADERS_READ = new AttributeKey<>(new UnsafeValueType(Boolean.class));

    private AttributeKey(Class<T> valueType) {
        super(valueType);
    }

    private AttributeKey(UnsafeValueType unsafeValueType) {
        super(unsafeValueType);
    }
}
