package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucket operation.
 */
public final class PutBucketRequest extends RequestModel {
    private final String bucket;
    private final CreateBucketConfiguration createBucketConfiguration;

    private PutBucketRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.createBucketConfiguration = builder.createBucketConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket. The name of a bucket must comply with the following naming conventions:*   The name can contain only lowercase letters, digits, and hyphens (-).*   It must start and end with a lowercase letter or a digit.*   The name must be 3 to 63 characters in length.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The access control list (ACL) of the bucket to be created. Valid values:*   public-read-write*   public-read*   private (default)For more information, see [Bucket ACL](~~31843~~).
     */
    public String acl() {
        String value = headers.get("x-oss-acl");
        return value;
    }

    /**
     * The ID of the resource group.*   If you include the header in the request and specify the ID of the resource group, the bucket that you create belongs to the resource group. If the specified resource group ID is rg-default-id, the bucket that you create belongs to the default resource group.*   If you do not include the header in the request, the bucket that you create belongs to the default resource group.You can obtain the ID of a resource group in the Resource Management console or by calling the ListResourceGroups operation. For more information, see [View basic information of a resource group](~~151181~~) and [ListResourceGroups](~~158855~~).  You cannot configure a resource group for an Anywhere Bucket.
     */
    public String resourceGroupId() {
        String value = headers.get("x-oss-resource-group-id");
        return value;
    }

    /**
     * The tags for buckets，for example 1=v1&amp;k2=v2。
     */
    public String bucketTagging() {
        String value = headers.get("x-oss-bucket-tagging");
        return value;
    }

    /**
     * The container that stores the request body.
     */
    public CreateBucketConfiguration createBucketConfiguration() {
        return createBucketConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private CreateBucketConfiguration createBucketConfiguration;

        private Builder() {
            super();
        }

        private Builder(PutBucketRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.createBucketConfiguration = request.createBucketConfiguration;
        }

        /**
         * The name of the bucket. The name of a bucket must comply with the following naming conventions:*   The name can contain only lowercase letters, digits, and hyphens (-).*   It must start and end with a lowercase letter or a digit.*   The name must be 3 to 63 characters in length.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The access control list (ACL) of the bucket to be created. Valid values:*   public-read-write*   public-read*   private (default)For more information, see [Bucket ACL](~~31843~~).
         */
        public Builder acl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-acl", value);
            return this;
        }

        /**
         * The ID of the resource group.*   If you include the header in the request and specify the ID of the resource group, the bucket that you create belongs to the resource group. If the specified resource group ID is rg-default-id, the bucket that you create belongs to the default resource group.*   If you do not include the header in the request, the bucket that you create belongs to the default resource group.You can obtain the ID of a resource group in the Resource Management console or by calling the ListResourceGroups operation. For more information, see [View basic information of a resource group](~~151181~~) and [ListResourceGroups](~~158855~~).  You cannot configure a resource group for an Anywhere Bucket.
         */
        public Builder resourceGroupId(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-resource-group-id", value);
            return this;
        }

        /**
         * Specify the bucket tags, such as k1=v1&amp;k2=v2.
         */
        public Builder bucketTagging(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-bucket-tagging", value);
            return this;
        }

        /**
         * The container that stores the request body.
         */
        public Builder createBucketConfiguration(CreateBucketConfiguration value) {
            requireNonNull(value);
            this.createBucketConfiguration = value;
            return this;
        }

        public PutBucketRequest build() {
            return new PutBucketRequest(this);
        }
    }

}
