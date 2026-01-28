package com.aliyun.sdk.service.oss2.models;

/**
 * Sorting order: asc or desc.
 */
public enum OrderType {

    ASC("asc"),
    DESC("desc"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a OrderType instance.
     */
    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a OrderType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed OrderType object, or UNKNOWN if unable to parse.
     */
    public static OrderType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        OrderType[] items = OrderType.values();
        for (OrderType item : items) {
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