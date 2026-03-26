package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutObjectRetention operation.
 */
public final class PutObjectRetentionRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final Boolean bypassGovernanceRetention;
    private final Retention retention;

    private PutObjectRetentionRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.bypassGovernanceRetention = builder.bypassGovernanceRetention;
        this.retention = builder.retention;
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
     * The version ID of the object.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    /**
     * Whether to bypass the governance mode retention.
     */
    public Boolean bypassGovernanceRetention() {
        return bypassGovernanceRetention;
    }

    /**
     * The container for object retention configuration.
     */
    public Retention retention() {
        return retention;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private Boolean bypassGovernanceRetention;
        private Retention retention;

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
         * The version ID of the object.
         */
        public Builder versionId(String value) {
            this.parameters.put("versionId", value);
            return this;
        }

        /**
         * Whether to bypass the governance mode retention.
         */
        public Builder bypassGovernanceRetention(Boolean value) {
            this.bypassGovernanceRetention = value;
            return this;
        }

        /**
         * The container for object retention configuration.
         */
        public Builder retention(Retention value) {
            this.retention = value;
            return this;
        }

        public PutObjectRetentionRequest build() {
            return new PutObjectRetentionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutObjectRetentionRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.bypassGovernanceRetention = request.bypassGovernanceRetention;
            this.retention = request.retention;
        }
    }
}
