package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutObjectLegalHold operation.
 */
public final class PutObjectLegalHoldRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final LegalHold legalHold;

    private PutObjectLegalHoldRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.legalHold = builder.legalHold;
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
     * The container for object legal hold configuration.
     */
    public LegalHold legalHold() {
        return legalHold;
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
        private LegalHold legalHold;

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
         * The container for object legal hold configuration.
         */
        public Builder legalHold(LegalHold value) {
            this.legalHold = value;
            return this;
        }

        public PutObjectLegalHoldRequest build() {
            return new PutObjectLegalHoldRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutObjectLegalHoldRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.legalHold = request.legalHold;
        }
    }
}
