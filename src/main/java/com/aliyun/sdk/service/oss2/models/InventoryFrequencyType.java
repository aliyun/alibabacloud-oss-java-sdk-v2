package com.aliyun.sdk.service.oss2.models;

/**
 * The frequency that inventory lists are exported
 */
public enum InventoryFrequencyType {

    DAILY("Daily"),
    WEEKLY("Weekly"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a InventoryFrequencyType instance.
     */
    private final String value;

    InventoryFrequencyType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a InventoryFrequencyType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed InventoryFrequencyTypeType object, or UNKNOWN if unable to parse.
     */
    public static InventoryFrequencyType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        InventoryFrequencyType[] items = InventoryFrequencyType.values();
        for (InventoryFrequencyType item : items) {
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
