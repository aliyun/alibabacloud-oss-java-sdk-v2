package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The metadata inventory table configuration returned by the service.
 */
public final class InventoryTableConfigurationResult {
    @JacksonXmlProperty(localName = "ConfigurationState")
    private String configurationState;

    @JacksonXmlProperty(localName = "TableStatus")
    private String tableStatus;

    @JacksonXmlProperty(localName = "TableName")
    private String tableName;

    @JacksonXmlProperty(localName = "TableArn")
    private String tableArn;

    @JacksonXmlProperty(localName = "EncryptionConfiguration")
    private MetadataTableEncryptionConfiguration encryptionConfiguration;

    @JacksonXmlProperty(localName = "Error")
    private MetadataTableError error;

    public InventoryTableConfigurationResult() {
    }

    /**
     * The state of the inventory metadata table.
     * Valid values: ENABLED, DISABLED.
     */
    public String configurationState() {
        return configurationState;
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

    public MetadataTableEncryptionConfiguration encryptionConfiguration() {
        return encryptionConfiguration;
    }

    public MetadataTableError error() {
        return error;
    }
}
