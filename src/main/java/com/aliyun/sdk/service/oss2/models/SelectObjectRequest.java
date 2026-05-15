package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.SelectRequest;

import static java.util.Objects.requireNonNull;

/**
 * The request for the SelectObject operation.
 */
public final class SelectObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final SelectRequest selectRequest;

    private SelectObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.selectRequest = builder.selectRequest;
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
     * The container that stores the SQL statement.
     */
    public SelectRequest selectRequest() {
        return selectRequest;
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
        private SelectRequest selectRequest;

        private Builder() {
            super();
        }

        private Builder(SelectObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.selectRequest = request.selectRequest;
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
         * The container that stores the SQL statement.
         */
        public Builder selectRequest(SelectRequest value) {
            requireNonNull(value);
            this.selectRequest = value;
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        public SelectObjectRequest build() {
            return new SelectObjectRequest(this);
        }
    }
}
