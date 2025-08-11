package com.aliyun.sdk.service.oss2.transport;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class RequestContext {
    private final Map<Key<?>, Object> attributes;

    private RequestContext(Map<? extends Key<?>, ?> attributes) {
        this.attributes = new HashMap<>(attributes);
    }

    public static RequestContext empty() {
        return new RequestContext(new HashMap<>());
    }

    public <T> boolean containsKey(Key<T> typedKey) {
        return this.attributes.containsKey(typedKey);
    }

    public <T> T get(Key<T> key) {
        requireNonNull(key, "Key to retrieve must not be null.");
        return key.convertValue(this.attributes.get(key));
    }

    public <T> RequestContext put(Key<T> key, T value) {
        requireNonNull(key, "Key to set must not be null.");
        this.attributes.put(key, value);
        return this;
    }

    public <T> RequestContext putIfAbsent(Key<T> key, T value) {
        requireNonNull(key, "Key to set must not be null.");
        this.attributes.putIfAbsent(key, value);
        return this;
    }

    public RequestContext merge(RequestContext lowerPrecedence) {
        Map<Key<?>, Object> copiedConfiguration = new HashMap<>(this.attributes);
        lowerPrecedence.attributes.forEach(copiedConfiguration::putIfAbsent);
        return new RequestContext(copiedConfiguration);
    }

    public RequestContext copy() {
        Map<Key<?>, Object> map = new HashMap<>();
        this.attributes.forEach((key, value) -> {
            key.validateValue(value);
            map.put(key, value);
        });
        return new RequestContext(map);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RequestContext)) {
            return false;
        }
        RequestContext rhs = (RequestContext) obj;
        if (attributes.size() != rhs.attributes.size()) {
            return false;
        }

        for (Key<?> lhsKey : attributes.keySet()) {
            Object lhsValue = get(lhsKey);
            Object rhsValue = rhs.get(lhsKey);
            if (!Objects.equals(lhsValue, rhsValue)) {
                return false;
            }
        }

        return true;
    }

    public enum HttpCompletionOption {
        /**
         * The operation should complete after reading the entire response including the content
         */
        ResponseContentRead,

        /**
         * The operation should complete as soon as a response is available and headers are read. The content is not read yet.
         */
        ResponseHeadersRead
    }

    public static class Key<T> {
        public static final Key<HttpCompletionOption> HTTP_COMPLETION_OPTION = new Key<>(new Key.UnsafeValueType(HttpCompletionOption.class));
        public static final Key<Duration> READWRITE_TIMEOUT = new Key<>(new Key.UnsafeValueType(Duration.class));

        private final Class<?> valueType;

        protected Key(Class<T> valueType) {
            this.valueType = valueType;
        }

        protected Key(UnsafeValueType unsafeValueType) {
            this.valueType = unsafeValueType.valueType;
        }

        final void validateValue(Object value) {
            if (value != null) {
                if (!this.valueType.isAssignableFrom(value.getClass())) {
                    throw new IllegalArgumentException(String.format(
                            "Invalid option: %s. Required value of type %s, but was %s.",
                            this, this.valueType, value.getClass()));
                }
            }

        }

        @SuppressWarnings("unchecked")
        public final T convertValue(Object value) {
            this.validateValue(value);
            return (T) this.valueType.cast(value);
        }

        protected static class UnsafeValueType {
            private final Class<?> valueType;

            public UnsafeValueType(Class<?> valueType) {
                this.valueType = valueType;
            }
        }
    }
}