package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The metadata journal table configuration returned by the service.
 */
public final class JournalTableConfigurationResult {
    @JacksonXmlProperty(localName = "TableStatus")
    private String tableStatus;

    @JacksonXmlProperty(localName = "TableName")
    private String tableName;

    @JacksonXmlProperty(localName = "TableArn")
    private String tableArn;

    @JacksonXmlProperty(localName = "RecordExpiration")
    private MetadataTableRecordExpiration recordExpiration;

    @JacksonXmlProperty(localName = "EncryptionConfiguration")
    private MetadataTableEncryptionConfiguration encryptionConfiguration;

    @JacksonXmlProperty(localName = "Error")
    private MetadataTableError error;

    public JournalTableConfigurationResult() {
    }

    /**
     * The creation state of a metadata table.
     * Valid values: CREATING, BACKFILLING, ACTIVE, FAILED.
     */
    public String tableStatus() {
        return tableStatus;
    }

    public String tableName() {
        return tableName;
    }

    public String tableArn() {
        return tableArn;
    }

    public MetadataTableRecordExpiration recordExpiration() {
        return recordExpiration;
    }

    public MetadataTableEncryptionConfiguration encryptionConfiguration() {
        return encryptionConfiguration;
    }

    public MetadataTableError error() {
        return error;
    }
}
