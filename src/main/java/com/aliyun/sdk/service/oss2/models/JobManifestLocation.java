package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The location of the manifest file for a batch job.
 */
@JacksonXmlRootElement(localName = "Location")
public final class JobManifestLocation {

    @JacksonXmlProperty(localName = "ETag")
    private String eTag;

    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;

    @JacksonXmlProperty(localName = "Object")
    private String object;

    @JacksonXmlProperty(localName = "VersionId")
    private String versionId;

    public JobManifestLocation() {
    }

    private JobManifestLocation(Builder builder) {
        this.eTag = builder.eTag;
        this.bucket = builder.bucket;
        this.object = builder.object;
        this.versionId = builder.versionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String eTag() {
        return this.eTag;
    }

    public String bucket() {
        return this.bucket;
    }

    public String object() {
        return this.object;
    }

    public String versionId() {
        return this.versionId;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String eTag;
        private String bucket;
        private String object;
        private String versionId;

        private Builder() {
            super();
        }

        private Builder(JobManifestLocation from) {
            this.eTag = from.eTag;
            this.bucket = from.bucket;
            this.object = from.object;
            this.versionId = from.versionId;
        }

        public Builder eTag(String value) {
            this.eTag = value;
            return this;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder object(String value) {
            requireNonNull(value);
            this.object = value;
            return this;
        }

        public Builder versionId(String value) {
            this.versionId = value;
            return this;
        }

        public JobManifestLocation build() {
            return new JobManifestLocation(this);
        }
    }
}
