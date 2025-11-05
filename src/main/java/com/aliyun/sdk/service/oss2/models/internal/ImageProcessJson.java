package com.aliyun.sdk.service.oss2.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import static java.util.Objects.requireNonNull;

/**
 * The result for the ProcessObject operation.
 */
public final class ImageProcessJson {
    @JsonProperty("bucket")
    private String bucket;

    @JsonProperty("fileSize")
    private Long fileSize;

    @JsonProperty("object")
    private String object;

    @JsonProperty("status")
    private String status;

    public ImageProcessJson() {
    }

    private ImageProcessJson(Builder builder) {
        this.bucket = builder.bucket;
        this.fileSize = builder.fileSize;
        this.object = builder.object;
        this.status = builder.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return this.bucket;
    }

    /**
     * The size of the file.
     */
    public Long fileSize() {
        return this.fileSize;
    }

    /**
     * The name of the object.
     */
    public String object() {
        return this.object;
    }

    /**
     * The status of the process.
     */
    public String status() {
        return this.status;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String bucket;
        private Long fileSize;
        private String object;
        private String status;

        private Builder() {
            super();
        }

        private Builder(ImageProcessJson from) {
            this.bucket = from.bucket;
            this.fileSize = from.fileSize;
            this.object = from.object;
            this.status = from.status;
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
         * The size of the file.
         */
        public Builder fileSize(Long value) {
            this.fileSize = value;
            return this;
        }

        /**
         * The name of the object.
         */
        public Builder object(String value) {
            requireNonNull(value);
            this.object = value;
            return this;
        }

        /**
         * The status of the process.
         */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }

        public ImageProcessJson build() {
            return new ImageProcessJson(this);
        }
    }
}
