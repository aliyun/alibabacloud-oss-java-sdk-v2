package com.aliyun.sdk.service.oss2.agentic.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The summary of a bucket space.
 */
public final class BucketSpaceSummary {
    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Location")
    private String location;

    @JacksonXmlProperty(localName = "CreationDate")
    private String creationDate;

    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    public BucketSpaceSummary() {
    }

    /**
     * The name of the bucket space.
     */
    public String name() { return this.name; }

    /**
     * The location of the bucket space.
     */
    public String location() { return this.location; }

    /**
     * The creation time of the bucket space.
     */
    public String creationDate() { return this.creationDate; }

    /**
     * The storage class of the bucket space.
     */
    public String storageClass() { return this.storageClass; }
}
