package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * The object metadata returned.
 */
@JacksonXmlRootElement(localName = "ObjectSummary")
public final class ObjectSummary {
    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    @JacksonXmlProperty(localName = "Owner")
    private Owner owner;

    @JacksonXmlProperty(localName = "TransitionTime")
    private Instant transitionTime;

    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "LastModified")
    private Instant lastModified;

    @JacksonXmlProperty(localName = "ETag")
    private String eTag;

    @JacksonXmlProperty(localName = "Type")
    private String type;

    @JacksonXmlProperty(localName = "Size")
    private Long size;

    @JacksonXmlProperty(localName = "RestoreInfo")
    private String restoreInfo;

    public ObjectSummary() {
    }

    private ObjectSummary(Builder builder) {
        this.storageClass = builder.storageClass;
        this.owner = builder.owner;
        this.transitionTime = builder.transitionTime;
        this.key = builder.key;
        this.lastModified = builder.lastModified;
        this.eTag = builder.eTag;
        this.type = builder.type;
        this.size = builder.size;
        this.restoreInfo = builder.restoreInfo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The storage class of the object.
     */
    public String storageClass() {
        return this.storageClass;
    }

    /**
     * The container for the information about the bucket owner.
     */
    public Owner owner() {
        return this.owner;
    }

    /**
     * The time when the storage class of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
     */
    public Instant transitionTime() {
        return this.transitionTime;
    }

    /**
     * The key of the object.
     */
    public String key() {
        return this.key;
    }

    /**
     * The time when the object was last modified.
     */
    public Instant lastModified() {
        return this.lastModified;
    }

    /**
     * The ETag that is generated when an object is created. ETags are used to identify the content of objects.*   For an object that is created by calling the PutObject operation, the ETag value of the object is the MD5 hash of its content.*   For an object that is created by using another method, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule.*   The ETag of an object can be used to check whether the object content is modified. We recommend that you use the MD5 hash of an object rather than the ETag of it to verify data integrity.
     */
    public String eTag() {
        return this.eTag;
    }

    /**
     * The types of the returned objects.*   Normal: Objects created by using simple upload.*   Multipart: Objects created by using multipart upload.*   Appendable: Objects created by using append upload. You can append content to objects only of the Appendable type.
     */
    public String type() {
        return this.type;
    }

    /**
     * The sizes of the returned objects. Unit: byte.
     */
    public Long size() {
        return this.size;
    }

    /**
     * The restoration status of the object.
     */
    public String restoreInfo() {
        return this.restoreInfo;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String storageClass;
        private Owner owner;
        private Instant transitionTime;
        private String key;
        private Instant lastModified;
        private String eTag;
        private String type;
        private Long size;
        private String restoreInfo;

        private Builder() {
            super();
        }

        private Builder(ObjectSummary from) {
            this.storageClass = from.storageClass;
            this.owner = from.owner;
            this.transitionTime = from.transitionTime;
            this.key = from.key;
            this.lastModified = from.lastModified;
            this.eTag = from.eTag;
            this.type = from.type;
            this.size = from.size;
            this.restoreInfo = from.restoreInfo;
        }

        /**
         * The storage class of the object.
         */
        public Builder storageClass(String value) {
            //requireNonNull(value);
            this.storageClass = value;
            return this;
        }

        /**
         * The container for the information about the bucket owner.
         */
        public Builder owner(Owner value) {
            //requireNonNull(value);
            this.owner = value;
            return this;
        }

        /**
         * The time when the storage class of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
         */
        public Builder transitionTime(Instant value) {
            //requireNonNull(value);
            this.transitionTime = value;
            return this;
        }

        /**
         * The key of the object.
         */
        public Builder key(String value) {
            //requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The time when the object was last modified.
         */
        public Builder lastModified(Instant value) {
            //requireNonNull(value);
            this.lastModified = value;
            return this;
        }

        /**
         * The ETag that is generated when an object is created. ETags are used to identify the content of objects.*   For an object that is created by calling the PutObject operation, the ETag value of the object is the MD5 hash of its content.*   For an object that is created by using another method, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule.*   The ETag of an object can be used to check whether the object content is modified. We recommend that you use the MD5 hash of an object rather than the ETag of it to verify data integrity.
         */
        public Builder eTag(String value) {
            //requireNonNull(value);
            this.eTag = value;
            return this;
        }

        /**
         * The types of the returned objects.*   Normal: Objects created by using simple upload.*   Multipart: Objects created by using multipart upload.*   Appendable: Objects created by using append upload. You can append content to objects only of the Appendable type.
         */
        public Builder type(String value) {
            //requireNonNull(value);
            this.type = value;
            return this;
        }

        /**
         * The sizes of the returned objects. Unit: byte.
         */
        public Builder size(Long value) {
            //requireNonNull(value);
            this.size = value;
            return this;
        }

        /**
         * The restoration status of the object.
         */
        public Builder restoreInfo(String value) {
            //requireNonNull(value);
            this.restoreInfo = value;
            return this;
        }

        public ObjectSummary build() {
            return new ObjectSummary(this);
        }
    }
}
