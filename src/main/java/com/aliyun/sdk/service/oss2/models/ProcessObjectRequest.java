package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ProcessObject operation.
 */
public final class ProcessObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final String process;

    private ProcessObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.process = builder.process;
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
     * The request process.
     */
    public String process() {
        return process;
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
        private String process;

        private Builder() {
            super();
        }

        private Builder(ProcessObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.process = request.process;
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
         * The request body.
         */
        public Builder process(String value) {
            requireNonNull(value);
            this.process = value;
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

        public ProcessObjectRequest build() {
            return new ProcessObjectRequest(this);
        }
    }

}
