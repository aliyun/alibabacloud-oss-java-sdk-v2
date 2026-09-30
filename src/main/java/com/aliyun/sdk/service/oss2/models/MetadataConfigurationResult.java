package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The metadata table configuration returned by the service.
 */
public final class MetadataConfigurationResult {
    @JacksonXmlProperty(localName = "DestinationResult")
    private MetadataTableDestinationResult destinationResult;

    @JacksonXmlProperty(localName = "JournalTableConfigurationResult")
    private JournalTableConfigurationResult journalTableConfigurationResult;

    @JacksonXmlProperty(localName = "InventoryTableConfigurationResult")
    private InventoryTableConfigurationResult inventoryTableConfigurationResult;

    public MetadataConfigurationResult() {
    }

    public MetadataTableDestinationResult destinationResult() {
        return destinationResult;
    }

    public JournalTableConfigurationResult journalTableConfigurationResult() {
        return journalTableConfigurationResult;
    }

    public InventoryTableConfigurationResult inventoryTableConfigurationResult() {
        return inventoryTableConfigurationResult;
    }
}
