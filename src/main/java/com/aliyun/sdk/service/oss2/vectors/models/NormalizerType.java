package com.aliyun.sdk.service.oss2.vectors.models;

/**
 * The score normalizer of a compound retriever component. It is supported by the
 * weight retriever only.
 */
public enum NormalizerType {

    NONE("none"),
    MIN_MAX("minMax"),
    L2("l2"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a NormalizerType instance.
     */
    private final String value;

    NormalizerType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a NormalizerType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed NormalizerType object, or UNKNOWN if unable to parse.
     */
    public static NormalizerType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        NormalizerType[] items = NormalizerType.values();
        for (NormalizerType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return UNKNOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.value;
    }
}
