package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 * Destination configuration for data pipeline.
 */
@JacksonXmlRootElement(localName = "Destination")
public final class DataPipelineDestination {
    @JacksonXmlProperty(localName = "VectorBucketName")
    private String vectorBucketName;

    @JacksonXmlProperty(localName = "VectorKeyPrefix")
    private String vectorKeyPrefix;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "VectorIndexNames")
    private List<String> vectorIndexNames;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ObjectTagToMetadata")
    private List<String> objectTagToMetadata;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "UsermetaToMetadata")
    private List<String> usermetaToMetadata;

    public DataPipelineDestination() {
    }

    private DataPipelineDestination(Builder builder) {
        this.vectorBucketName = builder.vectorBucketName;
        this.vectorKeyPrefix = builder.vectorKeyPrefix;
        this.vectorIndexNames = builder.vectorIndexNames;
        this.objectTagToMetadata = builder.objectTagToMetadata;
        this.usermetaToMetadata = builder.usermetaToMetadata;
    }

    public String vectorBucketName() {
        return vectorBucketName;
    }

    public String vectorKeyPrefix() {
        return vectorKeyPrefix;
    }

    public List<String> vectorIndexNames() {
        return vectorIndexNames;
    }

    public List<String> objectTagToMetadata() {
        return objectTagToMetadata;
    }

    public List<String> usermetaToMetadata() {
        return usermetaToMetadata;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String vectorBucketName;
        private String vectorKeyPrefix;
        private List<String> vectorIndexNames;
        private List<String> objectTagToMetadata;
        private List<String> usermetaToMetadata;

        public Builder vectorBucketName(String value) {
            this.vectorBucketName = value;
            return this;
        }

        public Builder vectorKeyPrefix(String value) {
            this.vectorKeyPrefix = value;
            return this;
        }

        public Builder vectorIndexNames(List<String> value) {
            this.vectorIndexNames = value;
            return this;
        }

        public Builder objectTagToMetadata(List<String> value) {
            this.objectTagToMetadata = value;
            return this;
        }

        public Builder usermetaToMetadata(List<String> value) {
            this.usermetaToMetadata = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineDestination from) {
            this.vectorBucketName = from.vectorBucketName;
            this.vectorKeyPrefix = from.vectorKeyPrefix;
            this.vectorIndexNames = from.vectorIndexNames;
            this.objectTagToMetadata = from.objectTagToMetadata;
            this.usermetaToMetadata = from.usermetaToMetadata;
        }

        public DataPipelineDestination build() {
            return new DataPipelineDestination(this);
        }
    }
}
