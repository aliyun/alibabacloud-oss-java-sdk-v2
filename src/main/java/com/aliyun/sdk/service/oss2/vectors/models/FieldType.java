package com.aliyun.sdk.service.oss2.vectors.models;

/**
 * The type of a field in the schema configuration of a fusion vector index.
 */
public enum FieldType {

    VECTOR("vector"),
    DOUBLE("double"),
    LONG("long"),
    BOOL("bool"),
    STRING("string"),
    IP("ip"),
    GEO_POINT("geoPoint"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a FieldType instance.
     */
    private final String value;

    FieldType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a FieldType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed FieldType object, or UNKNOWN if unable to parse.
     */
    public static FieldType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        FieldType[] items = FieldType.values();
        for (FieldType item : items) {
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
