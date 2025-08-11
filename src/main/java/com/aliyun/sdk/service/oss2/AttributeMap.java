package com.aliyun.sdk.service.oss2;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttributeMap {
    private final Map<Key<?>, Object> attributes;

    private AttributeMap(Map<? extends Key<?>, ?> attributes) {
        this.attributes = new HashMap<>(attributes);
    }

    public static AttributeMap empty() {
        return new AttributeMap(new HashMap<>());
    }

    public <T> boolean containsKey(Key<T> typedKey) {
        return this.attributes.containsKey(typedKey);
    }

    public <T> T get(Key<T> key) {
        Validate.notNull(key, "Key to retrieve must not be null.");
        return key.convertValue(this.attributes.get(key));
    }

    public <T> AttributeMap put(Key<T> key, T value) {
        Validate.notNull(key, "Key to set must not be null.");
        this.attributes.put(key, value);
        return this;
    }

    public <T> AttributeMap putIfAbsent(Key<T> key, T value) {
        Validate.notNull(key, "Key to set must not be null.");
        this.attributes.putIfAbsent(key, value);
        return this;
    }

    public AttributeMap merge(AttributeMap lowerPrecedence) {
        Map<Key<?>, Object> copiedConfiguration = new HashMap<>(this.attributes);
        lowerPrecedence.attributes.forEach(copiedConfiguration::putIfAbsent);
        return new AttributeMap(copiedConfiguration);
    }

    public AttributeMap copy() {
        Map<Key<?>, Object> map = new HashMap<>();
        this.attributes.forEach((key, value) -> {
            key.validateValue(value);
            map.put(key, value);
        });
        return new AttributeMap(map);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AttributeMap)) {
            return false;
        }
        AttributeMap rhs = (AttributeMap) obj;
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

    public abstract static class Key<T> {
        private final Class<?> valueType;

        protected Key(Class<T> valueType) {
            this.valueType = valueType;
        }

        protected Key(UnsafeValueType unsafeValueType) {
            this.valueType = unsafeValueType.valueType;
        }

        private static <T> void assertIsAssignableFrom(final Class<T> superType, final Class<?> type,
                                                       final String message, final Object... values) {
            if (!superType.isAssignableFrom(type)) {
                throw new IllegalArgumentException(String.format(message, values));
            }
        }

        final void validateValue(Object value) {
            if (value != null) {
                assertIsAssignableFrom(this.valueType, value.getClass(),
                        "Invalid option: %s. Required value of type %s, but was %s.", this, this.valueType, value.getClass());
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