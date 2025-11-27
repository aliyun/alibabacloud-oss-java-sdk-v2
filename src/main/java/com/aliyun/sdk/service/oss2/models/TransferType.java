package com.aliyun.sdk.service.oss2.models;

/**
 * The link used to transfer data in CRR
 */
public enum TransferType {

    INTERNAL("internal"),
    OSS_ACC("oss_acc"),
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a TransferType instance.
     */
    private final String value;

    TransferType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a TransferType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed TransferTypeType object, or UNKNOWN if unable to parse.
     */
    public static TransferType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        TransferType[] items = TransferType.values();
        for (TransferType item : items) {
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
