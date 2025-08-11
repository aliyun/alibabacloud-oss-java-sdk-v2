package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores complete multipart upload result for XML serialization.
 */
@JacksonXmlRootElement(localName = "CompleteMultipartUploadResult")
public final class CompleteMultipartUploadResultXml {
    @JacksonXmlProperty(localName = "EncodingType")
    private String encodingType;

    @JacksonXmlProperty(localName = "Location")
    private String location;

    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;

    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "ETag")
    private String eTag;

    public CompleteMultipartUploadResultXml() {
    }

    private CompleteMultipartUploadResultXml(Builder builder) {
        this.encodingType = builder.encodingType;
        this.location = builder.location;
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.eTag = builder.eTag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The encoding type of the object name in the response. If this parameter is specified in the request, the object name is encoded in the response.
     */
    public String encodingType() {
        return this.encodingType;
    }

    /**
     * The URL that is used to access the uploaded object.
     */
    public String location() {
        return this.location;
    }

    /**
     * The name of the bucket that contains the object you want to restore.
     */
    public String bucket() {
        return this.bucket;
    }

    /**
     * The name of the uploaded object.
     */
    public String key() {
        return this.key;
    }

    /**
     * The ETag that is generated when an object is created. ETags are used to identify the content of objects.If an object is created by calling the CompleteMultipartUpload operation, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule. The ETag of an object can be used to check whether the object content is modified. However, we recommend that you use the MD5 hash of an object rather than the ETag value of the object to verify data integrity.
     */
    public String eTag() {
        return this.eTag;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String encodingType;
        private String location;
        private String bucket;
        private String key;
        private String eTag;

        private Builder() {
            super();
        }

        private Builder(CompleteMultipartUploadResultXml from) {
            this.encodingType = from.encodingType;
            this.location = from.location;
            this.bucket = from.bucket;
            this.key = from.key;
            this.eTag = from.eTag;
        }

        /**
         * The encoding type of the object name in the response. If this parameter is specified in the request, the object name is encoded in the response.
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.encodingType = value;
            return this;
        }

        /**
         * The URL that is used to access the uploaded object.
         */
        public Builder location(String value) {
            requireNonNull(value);
            this.location = value;
            return this;
        }

        /**
         * The name of the bucket that contains the object you want to restore.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The name of the uploaded object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The ETag that is generated when an object is created. ETags are used to identify the content of objects.If an object is created by calling the CompleteMultipartUpload operation, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule. The ETag of an object can be used to check whether the object content is modified. However, we recommend that you use the MD5 hash of an object rather than the ETag value of the object to verify data integrity.
         */
        public Builder eTag(String value) {
            requireNonNull(value);
            this.eTag = value;
            return this;
        }

        public CompleteMultipartUploadResultXml build() {
            return new CompleteMultipartUploadResultXml(this);
        }
    }
}