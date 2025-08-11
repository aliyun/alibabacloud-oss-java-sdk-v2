package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the OptionObject operation.
 */
public final class OptionObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private OptionObjectRequest(Builder builder) {
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
     * The origin of the request. It is used to identify a cross-origin request. You can specify only one Origin header in a cross-origin request. By default, this header is left empty.
     */
    public String origin() {
        String value = headers.get("Origin");
        return value;
    }

    /**
     * The method to be used in the actual cross-origin request. You can specify only one Access-Control-Request-Method header in a cross-origin request. By default, this header is left empty.
     */
    public String accessControlRequestMethod() {
        String value = headers.get("Access-Control-Request-Method");
        return value;
    }

    /**
     * The custom headers to be sent in the actual cross-origin request. You can configure multiple custom headers in a cross-origin request. Custom headers are separated by commas (,). By default, this header is left empty.
     */
    public String accessControlRequestHeaders() {
        String value = headers.get("Access-Control-Request-Headers");
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

        private Builder(OptionObjectRequest request) {
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
         * The origin of the request. It is used to identify a cross-origin request. You can specify only one Origin header in a cross-origin request. By default, this header is left empty.
         */
        public Builder origin(String value) {
            requireNonNull(value);
            this.headers.put("Origin", value);
            return this;
        }

        /**
         * The method to be used in the actual cross-origin request. You can specify only one Access-Control-Request-Method header in a cross-origin request. By default, this header is left empty.
         */
        public Builder accessControlRequestMethod(String value) {
            requireNonNull(value);
            this.headers.put("Access-Control-Request-Method", value);
            return this;
        }

        /**
         * The custom headers to be sent in the actual cross-origin request. You can configure multiple custom headers in a cross-origin request. Custom headers are separated by commas (,). By default, this header is left empty.
         */
        public Builder accessControlRequestHeaders(String value) {
            requireNonNull(value);
            this.headers.put("Access-Control-Request-Headers", value);
            return this;
        }

        public OptionObjectRequest build() {
            return new OptionObjectRequest(this);
        }
    }

}
