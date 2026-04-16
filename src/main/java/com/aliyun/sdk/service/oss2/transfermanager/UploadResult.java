package com.aliyun.sdk.service.oss2.transfermanager;

import java.util.Map;

public class UploadResult {
    private final String uploadId;
    private final String etag;
    private final String versionId;
    private final String hashCrc64ecma;
    private final Map<String, String> headers;
    private final int statusCode;

    private UploadResult(Builder builder) {
        this.uploadId = builder.uploadId;
        this.etag = builder.etag;
        this.versionId = builder.versionId;
        this.hashCrc64ecma = builder.hashCrc64ecma;
        this.headers = builder.headers;
        this.statusCode = builder.statusCode;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String uploadId() {
        return uploadId;
    }

    public String etag() {
        return etag;
    }

    public String versionId() {
        return versionId;
    }

    public String hashCrc64ecma() {
        return hashCrc64ecma;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public int statusCode() {
        return statusCode;
    }

    public static class Builder {
        private String uploadId;
        private String etag;
        private String versionId;
        private String hashCrc64ecma;
        private Map<String, String> headers;
        private int statusCode;

        public Builder uploadId(String value) {
            this.uploadId = value;
            return this;
        }

        public Builder etag(String value) {
            this.etag = value;
            return this;
        }

        public Builder versionId(String value) {
            this.versionId = value;
            return this;
        }

        public Builder hashCrc64ecma(String value) {
            this.hashCrc64ecma = value;
            return this;
        }

        public Builder headers(Map<String, String> value) {
            this.headers = value;
            return this;
        }

        public Builder statusCode(int value) {
            this.statusCode = value;
            return this;
        }

        public UploadResult build() {
            return new UploadResult(this);
        }
    }
}
