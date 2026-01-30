package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Inventory optional field type enum
 * Used to specify optional fields included in OSS inventory reports
 */
public enum InventoryOptionalFieldType {

    /**
     * Object size
     */
    @JsonProperty("Size")
    SIZE("Size"),
    /**
     * Last modified date
     */
    @JsonProperty("LastModifiedDate")
    LAST_MODIFIED_DATE("LastModifiedDate"),
    /**
     * ETag value
     */
    @JsonProperty("ETag")
    E_TAG("ETag"),
    /**
     * Storage class
     */
    @JsonProperty("StorageClass")
    STORAGE_CLASS("StorageClass"),
    /**
     * Is multipart uploaded
     */
    @JsonProperty("IsMultipartUploaded")
    IS_MULTIPART_UPLOADED("IsMultipartUploaded"),
    /**
     * Encryption status
     */
    @JsonProperty("EncryptionStatus")
    ENCRYPTION_STATUS("EncryptionStatus"),
    /**
     * Transition time
     */
    @JsonProperty("TransitionTime")
    TRANSITION_TIME("TransitionTime"),
    /**
     * Object ACL
     */
    @JsonProperty("ObjectAcl")
    OBJECT_ACL("ObjectAcl"),
    /**
     * Tagging count
     */
    @JsonProperty("TaggingCount")
    TAGGING_COUNT("TaggingCount"),
    /**
     * Object type
     */
    @JsonProperty("ObjectType")
    OBJECT_TYPE("ObjectType"),
    /**
     * CRC64 checksum
     */
    @JsonProperty("Crc64")
    CRC64("Crc64"),
    /**
     * Unknown type
     */
    @JsonProperty("UNKNOWN")
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a InventoryOptionalFieldType instance.
     */
    private final String value;

    InventoryOptionalFieldType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a InventoryOptionalFieldType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed InventoryOptionalFieldTypeType object, or UNKNOWN if unable to parse.
     */
    public static InventoryOptionalFieldType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        InventoryOptionalFieldType[] items = InventoryOptionalFieldType.values();
        for (InventoryOptionalFieldType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return UNKNOWN;
    }

    /**
     * Gets the string representation of the enum value
     *
     * @return String representation of the enum value
     */
    @Override
    public String toString() {
        return this.value;
    }
}