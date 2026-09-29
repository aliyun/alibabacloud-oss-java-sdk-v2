package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The destination information of a bucket metadata configuration.
 */
public final class MetadataTableDestinationResult {
    @JacksonXmlProperty(localName = "TableBucketType")
    private String tableBucketType;

    @JacksonXmlProperty(localName = "TableBucketArn")
    private String tableBucketArn;

    @JacksonXmlProperty(localName = "TableNamespace")
    private String tableNamespace;

    public MetadataTableDestinationResult() {
    }

    public String tableBucketType() {
        return tableBucketType;
    }

    public String tableBucketArn() {
        return tableBucketArn;
    }

    public String tableNamespace() {
        return tableNamespace;
    }
}
