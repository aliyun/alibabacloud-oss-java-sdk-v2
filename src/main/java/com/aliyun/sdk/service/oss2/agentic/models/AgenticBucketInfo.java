package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ServerSideEncryptionRule;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The information of an agentic bucket.
 */
@JacksonXmlRootElement(localName = "AgenticBucketInfo")
public final class AgenticBucketInfo {
    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Owner")
    private String owner;

    @JacksonXmlProperty(localName = "Region")
    private String region;

    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    @JacksonXmlProperty(localName = "DataRedundancyType")
    private String dataRedundancyType;

    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "BucketResourceType")
    private String bucketResourceType;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;

    @JacksonXmlProperty(localName = "ACL")
    private String acl;

    @JacksonXmlProperty(localName = "PublicAccessBlock")
    private String publicAccessBlock;

    @JacksonXmlProperty(localName = "ServerSideEncryptionRule")
    private ServerSideEncryptionRule serverSideEncryptionRule;

    @JacksonXmlProperty(localName = "Versioning")
    private String versioning;

    @JacksonXmlProperty(localName = "BucketPolicy")
    private String bucketPolicy;

    public AgenticBucketInfo() {
    }

    private AgenticBucketInfo(Builder builder) {
        this.name = builder.name;
        this.owner = builder.owner;
        this.region = builder.region;
        this.storageClass = builder.storageClass;
        this.dataRedundancyType = builder.dataRedundancyType;
        this.status = builder.status;
        this.bucketResourceType = builder.bucketResourceType;
        this.createTime = builder.createTime;
        this.acl = builder.acl;
        this.publicAccessBlock = builder.publicAccessBlock;
        this.serverSideEncryptionRule = builder.serverSideEncryptionRule;
        this.versioning = builder.versioning;
        this.bucketPolicy = builder.bucketPolicy;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String name() { return this.name; }

    /**
     * The owner of the bucket.
     */
    public String owner() { return this.owner; }

    /**
     * The region in which the bucket is located.
     */
    public String region() { return this.region; }

    /**
     * The storage class of the bucket.
     */
    public String storageClass() { return this.storageClass; }

    /**
     * The data redundancy type of the bucket.
     */
    public String dataRedundancyType() { return this.dataRedundancyType; }

    /**
     * The status of the bucket.
     */
    public String status() { return this.status; }

    /**
     * The resource type of the bucket.
     */
    public String bucketResourceType() { return this.bucketResourceType; }

    /**
     * The creation time of the bucket.
     */
    public String createTime() { return this.createTime; }

    /**
     * The access control list (ACL) of the bucket.
     */
    public String acl() { return this.acl; }

    /**
     * The public access block configuration of the bucket.
     */
    public String publicAccessBlock() { return this.publicAccessBlock; }

    /**
     * The server-side encryption rule of the bucket.
     */
    public ServerSideEncryptionRule serverSideEncryptionRule() { return this.serverSideEncryptionRule; }

    /**
     * The versioning state of the bucket.
     */
    public String versioning() { return this.versioning; }

    /**
     * The policy of the bucket.
     */
    public String bucketPolicy() { return this.bucketPolicy; }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String name;
        private String owner;
        private String region;
        private String storageClass;
        private String dataRedundancyType;
        private String status;
        private String bucketResourceType;
        private String createTime;
        private String acl;
        private String publicAccessBlock;
        private ServerSideEncryptionRule serverSideEncryptionRule;
        private String versioning;
        private String bucketPolicy;

        private Builder() {}

        private Builder(AgenticBucketInfo from) {
            this.name = from.name;
            this.owner = from.owner;
            this.region = from.region;
            this.storageClass = from.storageClass;
            this.dataRedundancyType = from.dataRedundancyType;
            this.status = from.status;
            this.bucketResourceType = from.bucketResourceType;
            this.createTime = from.createTime;
            this.acl = from.acl;
            this.publicAccessBlock = from.publicAccessBlock;
            this.serverSideEncryptionRule = from.serverSideEncryptionRule;
            this.versioning = from.versioning;
            this.bucketPolicy = from.bucketPolicy;
        }

        /**
         * The name of the bucket.
         */
        public Builder name(String value) { this.name = value; return this; }

        /**
         * The owner of the bucket.
         */
        public Builder owner(String value) { this.owner = value; return this; }

        /**
         * The region in which the bucket is located.
         */
        public Builder region(String value) { this.region = value; return this; }

        /**
         * The storage class of the bucket.
         */
        public Builder storageClass(String value) { this.storageClass = value; return this; }

        /**
         * The data redundancy type of the bucket.
         */
        public Builder dataRedundancyType(String value) { this.dataRedundancyType = value; return this; }

        /**
         * The status of the bucket.
         */
        public Builder status(String value) { this.status = value; return this; }

        /**
         * The resource type of the bucket.
         */
        public Builder bucketResourceType(String value) { this.bucketResourceType = value; return this; }

        /**
         * The creation time of the bucket.
         */
        public Builder createTime(String value) { this.createTime = value; return this; }

        /**
         * The access control list (ACL) of the bucket.
         */
        public Builder acl(String value) { this.acl = value; return this; }

        /**
         * The public access block configuration of the bucket.
         */
        public Builder publicAccessBlock(String value) { this.publicAccessBlock = value; return this; }

        /**
         * The server-side encryption rule of the bucket.
         */
        public Builder serverSideEncryptionRule(ServerSideEncryptionRule value) { this.serverSideEncryptionRule = value; return this; }

        /**
         * The versioning state of the bucket.
         */
        public Builder versioning(String value) { this.versioning = value; return this; }

        /**
         * The policy of the bucket.
         */
        public Builder bucketPolicy(String value) { this.bucketPolicy = value; return this; }

        public AgenticBucketInfo build() {
            return new AgenticBucketInfo(this);
        }
    }
}
