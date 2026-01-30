package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Incremental inventory optional field type enum
 * Used to specify optional fields included in OSS incremental inventory reports
 */
public enum IncrementalInventoryOptionalFieldType {

    /**
     * Sequence number
     */
    @JsonProperty("SequenceNumber")
    SEQUENCE_NUMBER("SequenceNumber"),
    /**
     * Event type
     */
    @JsonProperty("RecordType")
    RECORD_TYPE("RecordType"),
    /**
     * Timestamp
     */
    @JsonProperty("RecordTimestamp")
    RECORD_TIMESTAMP("RecordTimestamp"),
    /**
     * Alibaba Cloud ID or Principal ID of the requester
     */
    @JsonProperty("Requester")
    REQUESTER("Requester"),
    /**
     * Source IP of the requester
     */
    @JsonProperty("SourceIp")
    SOURCE_IP("SourceIp"),
    /**
     * Unique identifier of the request
     */
    @JsonProperty("RequestId")
    REQUEST_ID("RequestId"),
    /**
     * Size of the object
     */
    @JsonProperty("Size")
    SIZE("Size"),
    /**
     * Storage class of the object
     */
    @JsonProperty("StorageClass")
    STORAGE_CLASS("StorageClass"),
    /**
     * Last modified date of the object
     */
    @JsonProperty("LastModifiedDate")
    LAST_MODIFIED_DATE("LastModifiedDate"),
    /**
     * ETag used to identify the content of an object
     */
    @JsonProperty("ETag")
    E_TAG("ETag"),
    /**
     * Whether the object is uploaded via multipart upload
     */
    @JsonProperty("IsMultipartUploaded")
    IS_MULTIPART_UPLOADED("IsMultipartUploaded"),
    /**
     * Type of the object
     */
    @JsonProperty("ObjectType")
    OBJECT_TYPE("ObjectType"),
    /**
     * Read/write permissions of the object
     */
    @JsonProperty("ObjectAcl")
    OBJECT_ACL("ObjectAcl"),
    /**
     * CRC64 of the object
     */
    @JsonProperty("Crc64")
    CRC64("Crc64"),
    /**
     * Whether the object is encrypted
     */
    @JsonProperty("EncryptionStatus")
    ENCRYPTION_STATUS("EncryptionStatus"),
    /**
     * Unknown type
     */
    @JsonProperty("UNKNOWN")
    UNKNOWN("UNKNOWN");

    /**
     * The actual serialized value for a IncrementalInventoryOptionalFieldType instance.
     */
    private final String value;

    IncrementalInventoryOptionalFieldType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a IncrementalInventoryOptionalFieldType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed IncrementalInventoryOptionalFieldType object, or UNKNOWN if unable to parse.
     */
    public static IncrementalInventoryOptionalFieldType fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        IncrementalInventoryOptionalFieldType[] items = IncrementalInventoryOptionalFieldType.values();
        for (IncrementalInventoryOptionalFieldType item : items) {
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
    
    /**
     * Gets the enum value from the string representation
     * @param value the string value to convert
     * @return the corresponding enum value
     */
    public static IncrementalInventoryOptionalFieldType fromValue(String value) {
        for (IncrementalInventoryOptionalFieldType type : IncrementalInventoryOptionalFieldType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}