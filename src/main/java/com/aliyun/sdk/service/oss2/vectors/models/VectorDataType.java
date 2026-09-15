package com.aliyun.sdk.service.oss2.vectors.models;

/**
 * The data type of a vector field in the schema configuration of a fusion vector index.
 */
public enum VectorDataType {

    FLOAT32("float32"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a VectorDataType instance.
     */
    private final String value;

    VectorDataType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a VectorDataType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed VectorDataType object, or UNKNOWN if unable to parse.
     */
    public static VectorDataType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        VectorDataType[] items = VectorDataType.values();
        for (VectorDataType item : items) {
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
