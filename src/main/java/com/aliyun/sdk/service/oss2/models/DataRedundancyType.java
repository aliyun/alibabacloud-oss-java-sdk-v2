package com.aliyun.sdk.service.oss2.models;

/**
 * The type of disaster recovery for a bucket.
 */
public enum DataRedundancyType {

    LRS("LRS"),
    ZRS("ZRS"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a DataRedundancyType instance.
     */
    private final String value;

    DataRedundancyType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a DataRedundancyType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed DataRedundancyTypeType object, or UNKNOWN if unable to parse.
     */
    public static DataRedundancyType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        DataRedundancyType[] items = DataRedundancyType.values();
        for (DataRedundancyType item : items) {
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
