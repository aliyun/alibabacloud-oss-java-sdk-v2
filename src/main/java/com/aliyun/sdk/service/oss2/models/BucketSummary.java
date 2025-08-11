package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the bucket information.
 */
@JacksonXmlRootElement(localName = "Bucket")
public final class BucketSummary {
    @JacksonXmlProperty(localName = "ExtranetEndpoint")
    private String extranetEndpoint;

    @JacksonXmlProperty(localName = "ResourceGroupId")
    private String resourceGroupId;

    @JacksonXmlProperty(localName = "Comment")
    private String comment;

    @JacksonXmlProperty(localName = "Location")
    private String location;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    @JacksonXmlProperty(localName = "CreationDate")
    private Instant creationDate;

    @JacksonXmlProperty(localName = "IntranetEndpoint")
    private String intranetEndpoint;

    @JacksonXmlProperty(localName = "Region")
    private String region;

    public BucketSummary() {
    }

    private BucketSummary(Builder builder) {
        this.extranetEndpoint = builder.extranetEndpoint;
        this.resourceGroupId = builder.resourceGroupId;
        this.comment = builder.comment;
        this.location = builder.location;
        this.name = builder.name;
        this.storageClass = builder.storageClass;
        this.creationDate = builder.creationDate;
        this.intranetEndpoint = builder.intranetEndpoint;
        this.region = builder.region;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The public endpoint of the bucket.
     */
    public String extranetEndpoint() {
        return this.extranetEndpoint;
    }

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public String resourceGroupId() {
        return this.resourceGroupId;
    }


    /**
     * Bucket description.
     */
    public String comment() {
        return this.comment;
    }

    /**
     * The region in which the bucket is located.
     */
    public String location() {
        return this.location;
    }

    /**
     * The name of the bucket.
     */
    public String name() {
        return this.name;
    }

    /**
     * The storage class of the bucket.
     */
    public String storageClass() {
        return this.storageClass;
    }

    /**
     * The region id.
     */
    public String region() {
        return this.region;
    }

    /**
     * The time when the bucket is created.
     */
    public Instant creationDate() {
        return this.creationDate;
    }

    /**
     * The internal endpoint of the bucket.
     */
    public String intranetEndpoint() {
        return this.intranetEndpoint;
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String extranetEndpoint;
        private String resourceGroupId;
        private String comment;
        private String location;
        private String name;
        private String storageClass;
        private Instant creationDate;
        private String intranetEndpoint;
        private String region;

        private Builder() {
            super();
        }

        private Builder(BucketSummary from) {
            this.extranetEndpoint = from.extranetEndpoint;
            this.resourceGroupId = from.resourceGroupId;
            this.region = from.region;
            this.comment = from.comment;
            this.location = from.location;
            this.name = from.name;
            this.storageClass = from.storageClass;
            this.creationDate = from.creationDate;
            this.intranetEndpoint = from.intranetEndpoint;
        }

        /**
         * The public endpoint of the bucket.
         */
        public Builder extranetEndpoint(String value) {
            requireNonNull(value);
            this.extranetEndpoint = value;
            return this;
        }

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public Builder resourceGroupId(String value) {
            requireNonNull(value);
            this.resourceGroupId = value;
            return this;
        }


        /**
         * Bucket description.
         */
        public Builder comment(String value) {
            requireNonNull(value);
            this.comment = value;
            return this;
        }


        /**
         * The region id.
         */
        public Builder region(String value) {
            requireNonNull(value);
            this.region = value;
            return this;
        }

        /**
         * The region in which the bucket is located.
         */
        public Builder location(String value) {
            requireNonNull(value);
            this.location = value;
            return this;
        }

        /**
         * The name of the bucket.
         */
        public Builder name(String value) {
            requireNonNull(value);
            this.name = value;
            return this;
        }

        /**
         * The storage class of the bucket.
         */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.storageClass = value;
            return this;
        }


        /**
         * The time when the bucket is created.
         */
        public Builder creationDate(Instant value) {
            requireNonNull(value);
            this.creationDate = value;
            return this;
        }

        /**
         * The internal endpoint of the bucket.
         */
        public Builder intranetEndpoint(String value) {
            requireNonNull(value);
            this.intranetEndpoint = value;
            return this;
        }

        public BucketSummary build() {
            return new BucketSummary(this);
        }
    }
}
