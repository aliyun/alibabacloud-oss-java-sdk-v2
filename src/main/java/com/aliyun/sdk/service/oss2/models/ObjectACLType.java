package com.aliyun.sdk.service.oss2.models;

/**
 * The access control list (ACL).
 */
public enum ObjectACLType {

    PRIVATE("private"),
    PUBLIC_READ("public-read"),
    PUBLIC_READ_WRITE("public-read-write"),
    DEFAULT("default"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a ObjectACLType instance.
     */
    private final String value;

    ObjectACLType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ObjectACLType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ObjectACLTypeType object, or UNKNOWN if unable to parse.
     */
    public static ObjectACLType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        ObjectACLType[] items = ObjectACLType.values();
        for (ObjectACLType item : items) {
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