package com.aliyun.sdk.service.oss2.models;

/**
 * A short description of struct
 */
public enum AccessMonitorStatusType {

    ENABLED("Enabled"),
    DISABLED("Disabled"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a AccessMonitorStatusType instance.
     */
    private final String value;

    AccessMonitorStatusType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a AccessMonitorStatusType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed AccessMonitorStatusTypeType object, or UNKNOWN if unable to parse.
     */
    public static AccessMonitorStatusType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        AccessMonitorStatusType[] items = AccessMonitorStatusType.values();
        for (AccessMonitorStatusType item : items) {
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
