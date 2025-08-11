package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores initiate multipart upload result for XML serialization.
 */
@JacksonXmlRootElement(localName = "InitiateMultipartUploadResult")
public final class InitiateMultipartUpload {
    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;

    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "UploadId")
    private String uploadId;

    @JacksonXmlProperty(localName = "EncodingType")
    private String encodingType;

    public InitiateMultipartUpload() {
    }

    private InitiateMultipartUpload(Builder builder) {
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.uploadId = builder.uploadId;
        this.encodingType = builder.encodingType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket to which the object is uploaded by the multipart upload task.
     */
    public String bucket() {
        return this.bucket;
    }

    /**
     * The name of the object that is uploaded by the multipart upload task.
     */
    public String key() {
        return this.key;
    }

    /**
     * The upload ID that uniquely identifies the multipart upload task. The upload ID is used to call UploadPart and CompleteMultipartUpload later.
     */
    public String uploadId() {
        return this.uploadId;
    }

    /**
     * The encoding type of the object name in the response. If the encoding-type parameter is specified in the request, the object name in the response is encoded.
     */
    public String encodingType() {
        return this.encodingType;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String bucket;
        private String key;
        private String uploadId;
        private String encodingType;

        private Builder() {
            super();
        }

        private Builder(InitiateMultipartUpload from) {
            this.bucket = from.bucket;
            this.key = from.key;
            this.uploadId = from.uploadId;
            this.encodingType = from.encodingType;
        }

        /**
         * The name of the bucket to which the object is uploaded by the multipart upload task.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The name of the object that is uploaded by the multipart upload task.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The upload ID that uniquely identifies the multipart upload task. The upload ID is used to call UploadPart and CompleteMultipartUpload later.
         */
        public Builder uploadId(String value) {
            requireNonNull(value);
            this.uploadId = value;
            return this;
        }

        /**
         * The encoding type of the object name in the response. If the encoding-type parameter is specified in the request, the object name in the response is encoded.
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.encodingType = value;
            return this;
        }

        public InitiateMultipartUpload build() {
            return new InitiateMultipartUpload(this);
        }
    }
}