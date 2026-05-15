package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateSelectObjectMeta operation.
 */
public final class CreateSelectObjectMetaRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final SelectMetaRequest selectMetaRequest;

    private CreateSelectObjectMetaRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.selectMetaRequest = builder.selectMetaRequest;
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
     * The container that stores the select object meta request configuration.
     */
    public SelectMetaRequest selectMetaRequest() {
        return selectMetaRequest;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private SelectMetaRequest selectMetaRequest;

        private Builder() {
            super();
        }

        private Builder(CreateSelectObjectMetaRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.selectMetaRequest = request.selectMetaRequest;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String bucket) {
            this.bucket = bucket;
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
         * The container that stores the select object meta request configuration.
         */
        public Builder selectMetaRequest(SelectMetaRequest value) {
            requireNonNull(value);
            this.selectMetaRequest = value;
            return this;
        }

        public CreateSelectObjectMetaRequest build() {
            return new CreateSelectObjectMetaRequest(this);
        }
    }
}
