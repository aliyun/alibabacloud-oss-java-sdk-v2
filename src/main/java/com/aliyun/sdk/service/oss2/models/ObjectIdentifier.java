package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Object identifier, containing the object's key and version ID (optional)
 */
@JacksonXmlRootElement(localName = "Object")
public final class ObjectIdentifier {
    @JacksonXmlProperty(localName = "Key")
    private final String key;

    @JacksonXmlProperty(localName = "VersionId")
    private final String versionId;

    private ObjectIdentifier(Builder builder) {
        this.key = builder.key;
        this.versionId = builder.versionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The key of the object to be deleted
     */
    public String key() {
        return key;
    }

    /**
     * The version ID of the object to be deleted
     */
    public String versionId() {
        return versionId;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String key;
        private String versionId;

        private Builder() {
            super();
        }

        private Builder(ObjectIdentifier objectIdentifier) {
            this.key = objectIdentifier.key;
            this.versionId = objectIdentifier.versionId;
        }

        /**
         * The key of the object to be deleted
         */
        public Builder key(String value) {
            this.key = value;
            return this;
        }

        /**
         * The version ID of the object to be deleted
         */
        public Builder versionId(String value) {
            this.versionId = value;
            return this;
        }

        public ObjectIdentifier build() {
            return new ObjectIdentifier(this);
        }
    }
}