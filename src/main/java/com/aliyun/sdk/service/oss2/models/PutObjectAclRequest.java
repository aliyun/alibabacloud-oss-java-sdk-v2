package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutObjectAcl operation.
 */
public final class PutObjectAclRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private PutObjectAclRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The name of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The access control list (ACL) of the object.
     */
    public String objectAcl() {
        String value = headers.get("x-oss-object-acl");
        return value;
    }

    /**
     * The version id of the object.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;

        private Builder() {
            super();
        }

        private Builder(PutObjectAclRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The name of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The access control list (ACL) of the object.
         */
        public Builder objectAcl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-object-acl", value);
            return this;
        }

        /**
         * The version id of the object.
         */
        public Builder versionId(String value) {
            requireNonNull(value);
            this.parameters.put("versionId", value);
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public PutObjectAclRequest.Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        public PutObjectAclRequest build() {
            return new PutObjectAclRequest(this);
        }
    }

}
