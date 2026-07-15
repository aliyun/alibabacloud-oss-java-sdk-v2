package com.aliyun.sdk.service.oss2.agentic.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The summary of an agentic bucket.
 */
public final class AgenticBucketSummary {
    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    @JacksonXmlProperty(localName = "DataRedundancyType")
    private String dataRedundancyType;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;

    public AgenticBucketSummary() {
    }

    /**
     * The name of the bucket.
     */
    public String name() { return this.name; }

    /**
     * The storage class of the bucket.
     */
    public String storageClass() { return this.storageClass; }

    /**
     * The data redundancy type of the bucket.
     */
    public String dataRedundancyType() { return this.dataRedundancyType; }

    /**
     * The creation time of the bucket.
     */
    public String createTime() { return this.createTime; }
}
