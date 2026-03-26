package com.aliyun.sdk.service.oss2.models;

/**
 * The retention mode of the object-level WORM configuration.
 */
public enum ObjectRetentionModeType {

    GOVERNANCE("GOVERNANCE"),
    COMPLIANCE("COMPLIANCE"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a ObjectRetentionModeType instance.
     */
    private final String value;

    ObjectRetentionModeType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ObjectRetentionModeType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ObjectRetentionModeType object, or UNKNOWN if unable to parse.
     */
    public static ObjectRetentionModeType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        ObjectRetentionModeType[] items = ObjectRetentionModeType.values();
        for (ObjectRetentionModeType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
