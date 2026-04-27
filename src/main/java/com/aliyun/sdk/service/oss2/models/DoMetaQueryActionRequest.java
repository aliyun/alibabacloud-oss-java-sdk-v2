package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DoMetaQueryAction operation.
 */
public final class DoMetaQueryActionRequest extends RequestModel {
    private final String bucket;
    private final BinaryData body;

    private DoMetaQueryActionRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.body = builder.body;
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
     * The action parameter.
     */
    public String action() {
        String value = parameters.get("action");
        return value;
    }

    public BinaryData body() {
        return body;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private BinaryData body;

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The action parameter.
         */
        public Builder action(String value) {
            requireNonNull(value);
            this.parameters.put("action", value);
            return this;
        }

        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;
        }

        public DoMetaQueryActionRequest build() {
            return new DoMetaQueryActionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DoMetaQueryActionRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.body = request.body;
        }
    }
}
