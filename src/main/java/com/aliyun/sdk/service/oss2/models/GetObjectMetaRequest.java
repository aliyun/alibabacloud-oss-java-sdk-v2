package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetObjectMeta operation.
 */
public final class GetObjectMetaRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private GetObjectMetaRequest(Builder builder) {
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
     * The full path of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The versionID of the object.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs. Default value: null
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

        private Builder(GetObjectMetaRequest request) {
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
         * The full path of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The versionID of the object.
         */
        public Builder versionId(String value) {
            requireNonNull(value);
            this.parameters.put("versionId", value);
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs. Default value: null
         */
        public GetObjectMetaRequest.Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        public GetObjectMetaRequest build() {
            return new GetObjectMetaRequest(this);
        }
    }

}
