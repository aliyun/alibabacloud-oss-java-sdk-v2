package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The information about a delete object.
 */
@JacksonXmlRootElement(localName = "Object")
public final class DeleteObject {
    @JacksonXmlProperty(localName = "Key")
    private final String key;

    @JacksonXmlProperty(localName = "VersionId")
    private final String versionId;

    private DeleteObject(Builder builder) {
        this.key = builder.key;
        this.versionId = builder.versionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the object that you want to delete.
     */
    public String key() {
        return key;
    }

    /**
     * The version ID of the object that you want to delete.
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

        private Builder(DeleteObject deleteObject) {
            this.key = deleteObject.key;
            this.versionId = deleteObject.versionId;
        }

        /**
         * The name of the object that you want to delete.
         */
        public Builder key(String value) {
            this.key = value;
            return this;
        }

        /**
         * The version ID of the object that you want to delete.
         */
        public Builder versionId(String value) {
            this.versionId = value;
            return this;
        }

        public DeleteObject build() {
            return new DeleteObject(this);
        }
    }

}
