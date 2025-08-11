package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The information about a delete object.
 */
@JacksonXmlRootElement(localName = "Deleted")
public final class DeletedInfo {
    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "VersionId")
    private String versionId;

    @JacksonXmlProperty(localName = "DeleteMarker")
    private Boolean deleteMarker;

    @JacksonXmlProperty(localName = "DeleteMarkerVersionId")
    private String deleteMarkerVersionId;

    public DeletedInfo() {
    }

    private DeletedInfo(Builder builder) {
        this.key = builder.key;
        this.versionId = builder.versionId;
        this.deleteMarker = builder.deleteMarker;
        this.deleteMarkerVersionId = builder.deleteMarkerVersionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the deleted object.
     */
    public String key() {
        return key;
    }

    /**
     * The version ID of the object that you deleted.
     */
    public String versionId() {
        return versionId;
    }

    /**
     * Indicates whether the deleted version is a delete marker.
     */
    public Boolean deleteMarker() {
        return deleteMarker;
    }

    /**
     * The version ID of the delete marker.
     */
    public String deleteMarkerVersionId() {
        return deleteMarkerVersionId;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String key;
        private String versionId;
        private Boolean deleteMarker;
        private String deleteMarkerVersionId;

        private Builder() {
            super();
        }

        private Builder(DeletedInfo deletedInfo) {
            this.key = deletedInfo.key;
            this.versionId = deletedInfo.versionId;
            this.deleteMarker = deletedInfo.deleteMarker;
            this.deleteMarkerVersionId = deletedInfo.deleteMarkerVersionId;
        }

        /**
         * The name of the deleted object.
         */
        public Builder key(String value) {
            this.key = value;
            return this;
        }

        /**
         * The version ID of the object that you deleted.
         */
        public Builder versionId(String value) {
            this.versionId = value;
            return this;
        }

        /**
         * Indicates whether the deleted version is a delete marker.
         */
        public Builder deleteMarker(Boolean value) {
            this.deleteMarker = value;
            return this;
        }

        /**
         * The version ID of the delete marker.
         */
        public Builder deleteMarkerVersionId(String value) {
            this.deleteMarkerVersionId = value;
            return this;
        }

        public DeletedInfo build() {
            return new DeletedInfo(this);
        }
    }

}
