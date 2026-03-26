package com.aliyun.sdk.service.oss2.models;

/**
 * The status of the object legal hold.
 */
public enum ObjectLegalHoldStatusType {

    ON("ON"),
    OFF("OFF"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a ObjectLegalHoldStatusType instance.
     */
    private final String value;

    ObjectLegalHoldStatusType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ObjectLegalHoldStatusType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ObjectLegalHoldStatusType object, or UNKNOWN if unable to parse.
     */
    public static ObjectLegalHoldStatusType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        ObjectLegalHoldStatusType[] items = ObjectLegalHoldStatusType.values();
        for (ObjectLegalHoldStatusType item : items) {
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
