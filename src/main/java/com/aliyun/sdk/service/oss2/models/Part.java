package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the uploaded parts.
 */
@JacksonXmlRootElement(localName = "Part")
public final class Part {
    @JacksonXmlProperty(localName = "PartNumber")
    private Long partNumber;

    @JacksonXmlProperty(localName = "ETag")
    private String eTag;

    @JacksonXmlProperty(localName = "LastModified")
    private Instant lastModified;

    @JacksonXmlProperty(localName = "Size")
    private Long size;

    @JacksonXmlProperty(localName = "HashCrc64ecma")
    private String hashCrc64ecma;

    public Part() {
    }

    private Part(Builder builder) {
        this.partNumber = builder.partNumber;
        this.eTag = builder.eTag;
        this.lastModified = builder.lastModified;
        this.size = builder.size;
        this.hashCrc64ecma = builder.hashCrc64ecma;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The number of parts.
     */
    public Long partNumber() {
        return this.partNumber;
    }

    /**
     * The ETag that is generated when the object is created. ETags are used to identify the content of objects.If an object is created by calling the CompleteMultipartUpload operation, the ETag is not the MD5 hash of the object content but a unique value calculated based on a specific rule.  The ETag of an object can be used to check whether the object content changes. We recommend that you use the MD5 hash of an object rather than the ETag of the object to verify data integrity.
     */
    public String eTag() {
        return this.eTag;
    }

    /**
     * The time when the part was uploaded.
     */
    public Instant lastModified() {
        return this.lastModified;
    }

    /**
     * The size of the uploaded part.
     */
    public Long size() {
        return this.size;
    }

    /**
     * The 64-bit CRC value of the object.
     * This value is calculated based on the ECMA-182 standard.
     */
    public String hashCrc64ecma() {
        return this.hashCrc64ecma;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long partNumber;
        private String eTag;
        private Instant lastModified;
        private Long size;
        private String hashCrc64ecma;

        private Builder() {
            super();
        }

        private Builder(Part from) {
            this.partNumber = from.partNumber;
            this.eTag = from.eTag;
            this.lastModified = from.lastModified;
            this.size = from.size;
            this.hashCrc64ecma = from.hashCrc64ecma;
        }

        /**
         * The number of parts.
         */
        public Builder partNumber(Long value) {
            requireNonNull(value);
            this.partNumber = value;
            return this;
        }

        /**
         * The ETag that is generated when the object is created. ETags are used to identify the content of objects.If an object is created by calling the CompleteMultipartUpload operation, the ETag is not the MD5 hash of the object content but a unique value calculated based on a specific rule.  The ETag of an object can be used to check whether the object content changes. We recommend that you use the MD5 hash of an object rather than the ETag of the object to verify data integrity.
         */
        public Builder eTag(String value) {
            requireNonNull(value);
            this.eTag = value;
            return this;
        }

        /**
         * The time when the part was uploaded.
         */
        public Builder lastModified(Instant value) {
            this.lastModified = value;
            return this;
        }

        /**
         * The size of the uploaded part.
         */
        public Builder size(Long value) {
            this.size = value;
            return this;
        }

        /**
         * The 64-bit CRC value of the object.
         * This value is calculated based on the ECMA-182 standard.
         */
        public Builder hashCrc64ecma(String value) {
            this.hashCrc64ecma = value;
            return this;
        }

        public Part build() {
            return new Part(this);
        }
    }
}
