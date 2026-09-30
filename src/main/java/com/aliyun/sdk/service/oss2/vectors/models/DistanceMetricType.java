package com.aliyun.sdk.service.oss2.vectors.models;

/**
 * The distance metric of a vector field in the schema configuration of a fusion vector index.
 */
public enum DistanceMetricType {

    EUCLIDEAN("euclidean"),
    COSINE("cosine"),
    INNER_PRODUCT("ip"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a DistanceMetricType instance.
     */
    private final String value;

    DistanceMetricType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a DistanceMetricType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed DistanceMetricType object, or UNKNOWN if unable to parse.
     */
    public static DistanceMetricType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        DistanceMetricType[] items = DistanceMetricType.values();
        for (DistanceMetricType item : items) {
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
