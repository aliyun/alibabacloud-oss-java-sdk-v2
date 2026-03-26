package com.aliyun.sdk.service.oss2.models;

/**
 * Object-level retention strategy pattern.
 */
public enum ObjectWormConfigurationModeType {

    GOVERNANCE("GOVERNANCE"),
    COMPLIANCE("COMPLIANCE"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a ObjectWormConfigurationModeType instance.
     */
    private final String value;

    ObjectWormConfigurationModeType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ObjectWormConfigurationModeType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ObjectWormConfigurationModeType object, or UNKNOWN if unable to parse.
     */
    public static ObjectWormConfigurationModeType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        ObjectWormConfigurationModeType[] items = ObjectWormConfigurationModeType.values();
        for (ObjectWormConfigurationModeType item : items) {
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
