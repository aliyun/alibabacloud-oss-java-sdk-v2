package com.aliyun.sdk.service.oss2.models;

/**
 * The format of the exported inventory list
 */
public enum InventoryFormatType {

    CSV("CSV"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a InventoryFormatType instance.
     */
    private final String value;

    InventoryFormatType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a InventoryFormatType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed InventoryFormatTypeType object, or UNKNOWN if unable to parse.
     */
    public static InventoryFormatType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        InventoryFormatType[] items = InventoryFormatType.values();
        for (InventoryFormatType item : items) {
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
