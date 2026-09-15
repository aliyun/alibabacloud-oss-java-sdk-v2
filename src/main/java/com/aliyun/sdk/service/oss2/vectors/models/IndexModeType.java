package com.aliyun.sdk.service.oss2.vectors.models;

/**
 * The mode of a vector index.
 */
public enum IndexModeType {

    STANDARD("standard"),
    FUSION("fusion"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a IndexModeType instance.
     */
    private final String value;

    IndexModeType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a IndexModeType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed IndexModeType object, or UNKNOWN if unable to parse.
     */
    public static IndexModeType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        IndexModeType[] items = IndexModeType.values();
        for (IndexModeType item : items) {
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
