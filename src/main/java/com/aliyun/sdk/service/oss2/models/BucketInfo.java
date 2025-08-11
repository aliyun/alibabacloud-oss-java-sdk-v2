package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the bucket information.
 */
@JacksonXmlRootElement(localName = "Bucket")
public final class BucketInfo {
    @JacksonXmlProperty(localName = "ExtranetEndpoint")
    private String extranetEndpoint;

    @JacksonXmlProperty(localName = "ResourceGroupId")
    private String resourceGroupId;

    @JacksonXmlProperty(localName = "ServerSideEncryptionRule")
    private BucketInfoServerSideEncryptionRule serverSideEncryptionRule;

    @JacksonXmlProperty(localName = "BucketPolicy")
    private BucketPolicy bucketPolicy;

    @JacksonXmlProperty(localName = "Comment")
    private String comment;

    @JacksonXmlProperty(localName = "BlockPublicAccess")
    private Boolean blockPublicAccess;

    @JacksonXmlProperty(localName = "AccessMonitor")
    private String accessMonitor;

    @JacksonXmlProperty(localName = "CrossRegionReplication")
    private String crossRegionReplication;

    @JacksonXmlProperty(localName = "Location")
    private String location;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    @JacksonXmlProperty(localName = "Versioning")
    private String versioning;

    @JacksonXmlProperty(localName = "CreationDate")
    private Instant creationDate;

    @JacksonXmlProperty(localName = "IntranetEndpoint")
    private String intranetEndpoint;

    @JacksonXmlProperty(localName = "TransferAcceleration")
    private String transferAcceleration;


    @JacksonXmlProperty(localName = "AccessControlList")
    private AccessControlList accessControlList;

    @JacksonXmlProperty(localName = "DataRedundancyType")
    private String dataRedundancyType;

    @JacksonXmlProperty(localName = "Owner")
    private Owner owner;

    public BucketInfo() {
    }

    private BucketInfo(Builder builder) {
        this.extranetEndpoint = builder.extranetEndpoint;
        this.resourceGroupId = builder.resourceGroupId;
        this.serverSideEncryptionRule = builder.serverSideEncryptionRule;
        this.bucketPolicy = builder.bucketPolicy;
        this.comment = builder.comment;
        this.blockPublicAccess = builder.blockPublicAccess;
        this.accessMonitor = builder.accessMonitor;
        this.crossRegionReplication = builder.crossRegionReplication;
        this.location = builder.location;
        this.name = builder.name;
        this.storageClass = builder.storageClass;
        this.versioning = builder.versioning;
        this.creationDate = builder.creationDate;
        this.intranetEndpoint = builder.intranetEndpoint;
        this.transferAcceleration = builder.transferAcceleration;
        this.accessControlList = builder.accessControlList;
        this.dataRedundancyType = builder.dataRedundancyType;
        this.owner = builder.owner;
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
     * The server-side encryption configurations of the bucket.
     */
    public BucketInfoServerSideEncryptionRule serverSideEncryptionRule() {
        return this.serverSideEncryptionRule;
    }

    /**
     * The log configurations of the bucket.
     */
    public BucketPolicy bucketPolicy() {
        return this.bucketPolicy;
    }

    /**
     * Bucket description.
     */
    public String comment() {
        return this.comment;
    }

    /**
     * Whether the bucket has been configured to block public access.
     */
    public Boolean blockPublicAccess() {
        return this.blockPublicAccess;
    }

    /**
     * Indicates whether access tracking is enabled for the bucket.Valid values:*   Enabled            *   Disabled
     */
    public String accessMonitor() {
        return this.accessMonitor;
    }

    /**
     * Indicates whether cross-region replication (CRR) is enabled for the bucket.Valid values:*   Enabled            *   Disabled
     */
    public String crossRegionReplication() {
        return this.crossRegionReplication;
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
     * The versioning status of the bucket.
     */
    public String versioning() {
        return this.versioning;
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

    /**
     * Indicates whether transfer acceleration is enabled for the bucket.Valid values:*   Enabled            *   Disabled
     */
    public String transferAcceleration() {
        return this.transferAcceleration;
    }

    /**
     * The ACL of the bucket.
     */
    public AccessControlList accessControlList() {
        return this.accessControlList;
    }

    /**
     * The redundancy type of the bucket.
     */
    public String dataRedundancyType() {
        return this.dataRedundancyType;
    }

    /**
     * The owner of the bucket.
     */
    public Owner owner() {
        return this.owner;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String extranetEndpoint;
        private String resourceGroupId;
        private BucketInfoServerSideEncryptionRule serverSideEncryptionRule;
        private BucketPolicy bucketPolicy;
        private String comment;
        private Boolean blockPublicAccess;
        private String accessMonitor;
        private String crossRegionReplication;
        private String location;
        private String name;
        private String storageClass;
        private String versioning;
        private Instant creationDate;
        private String intranetEndpoint;
        private String transferAcceleration;
        private AccessControlList accessControlList;
        private String dataRedundancyType;
        private Owner owner;

        private Builder() {
            super();
        }

        private Builder(BucketInfo from) {
            this.extranetEndpoint = from.extranetEndpoint;
            this.resourceGroupId = from.resourceGroupId;
            this.serverSideEncryptionRule = from.serverSideEncryptionRule;
            this.bucketPolicy = from.bucketPolicy;
            this.comment = from.comment;
            this.blockPublicAccess = from.blockPublicAccess;
            this.accessMonitor = from.accessMonitor;
            this.crossRegionReplication = from.crossRegionReplication;
            this.location = from.location;
            this.name = from.name;
            this.storageClass = from.storageClass;
            this.versioning = from.versioning;
            this.creationDate = from.creationDate;
            this.intranetEndpoint = from.intranetEndpoint;
            this.transferAcceleration = from.transferAcceleration;
            this.accessControlList = from.accessControlList;
            this.dataRedundancyType = from.dataRedundancyType;
            this.owner = from.owner;
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
         * The server-side encryption configurations of the bucket.
         */
        public Builder serverSideEncryptionRule(BucketInfoServerSideEncryptionRule value) {
            requireNonNull(value);
            this.serverSideEncryptionRule = value;
            return this;
        }

        /**
         * The log configurations of the bucket.
         */
        public Builder bucketPolicy(BucketPolicy value) {
            requireNonNull(value);
            this.bucketPolicy = value;
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
         * Whether the bucket has been configured to block public access.
         */
        public Builder blockPublicAccess(Boolean value) {
            requireNonNull(value);
            this.blockPublicAccess = value;
            return this;
        }

        /**
         * Indicates whether access tracking is enabled for the bucket.Valid values:*   Enabled            *   Disabled
         */
        public Builder accessMonitor(String value) {
            requireNonNull(value);
            this.accessMonitor = value;
            return this;
        }

        /**
         * Indicates whether cross-region replication (CRR) is enabled for the bucket.Valid values:*   Enabled            *   Disabled
         */
        public Builder crossRegionReplication(String value) {
            requireNonNull(value);
            this.crossRegionReplication = value;
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
         * The versioning status of the bucket.
         */
        public Builder versioning(String value) {
            requireNonNull(value);
            this.versioning = value;
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

        /**
         * Indicates whether transfer acceleration is enabled for the bucket.Valid values:*   Enabled            *   Disabled
         */
        public Builder transferAcceleration(String value) {
            requireNonNull(value);
            this.transferAcceleration = value;
            return this;
        }

        /**
         * The ACL of the bucket.
         */
        public Builder accessControlList(AccessControlList value) {
            requireNonNull(value);
            this.accessControlList = value;
            return this;
        }

        /**
         * The redundancy type of the bucket.
         */
        public Builder dataRedundancyType(String value) {
            requireNonNull(value);
            this.dataRedundancyType = value;
            return this;
        }

        /**
         * The owner of the bucket.
         */
        public Builder owner(Owner value) {
            requireNonNull(value);
            this.owner = value;
            return this;
        }

        public BucketInfo build() {
            return new BucketInfo(this);
        }
    }
}
