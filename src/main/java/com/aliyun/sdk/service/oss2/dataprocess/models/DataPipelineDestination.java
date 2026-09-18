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

    @JacksonXmlProperty(localName = "ImageEmbedding")
    private DataPipelineDestinationImageEmbedding imageEmbedding;

    @JacksonXmlProperty(localName = "ImageTextEmbedding")
    private DataPipelineDestinationImageTextEmbedding imageTextEmbedding;

    @JacksonXmlProperty(localName = "VideoFrameEmbedding")
    private DataPipelineDestinationVideoFrameEmbedding videoFrameEmbedding;

    @JacksonXmlProperty(localName = "VideoTextEmbedding")
    private DataPipelineDestinationVideoTextEmbedding videoTextEmbedding;

    @JacksonXmlProperty(localName = "DocumentChunkEmbedding")
    private DataPipelineDestinationDocumentChunkEmbedding documentChunkEmbedding;

    public DataPipelineDestination() {
    }

    private DataPipelineDestination(Builder builder) {
        this.vectorBucketName = builder.vectorBucketName;
        this.vectorKeyPrefix = builder.vectorKeyPrefix;
        this.vectorIndexNames = builder.vectorIndexNames;
        this.objectTagToMetadata = builder.objectTagToMetadata;
        this.usermetaToMetadata = builder.usermetaToMetadata;
        this.imageEmbedding = builder.imageEmbedding;
        this.imageTextEmbedding = builder.imageTextEmbedding;
        this.videoFrameEmbedding = builder.videoFrameEmbedding;
        this.videoTextEmbedding = builder.videoTextEmbedding;
        this.documentChunkEmbedding = builder.documentChunkEmbedding;
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

    public DataPipelineDestinationImageEmbedding imageEmbedding() {
        return imageEmbedding;
    }

    public DataPipelineDestinationImageTextEmbedding imageTextEmbedding() {
        return imageTextEmbedding;
    }

    public DataPipelineDestinationVideoFrameEmbedding videoFrameEmbedding() {
        return videoFrameEmbedding;
    }

    public DataPipelineDestinationVideoTextEmbedding videoTextEmbedding() {
        return videoTextEmbedding;
    }

    public DataPipelineDestinationDocumentChunkEmbedding documentChunkEmbedding() {
        return documentChunkEmbedding;
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
        private DataPipelineDestinationImageEmbedding imageEmbedding;
        private DataPipelineDestinationImageTextEmbedding imageTextEmbedding;
        private DataPipelineDestinationVideoFrameEmbedding videoFrameEmbedding;
        private DataPipelineDestinationVideoTextEmbedding videoTextEmbedding;
        private DataPipelineDestinationDocumentChunkEmbedding documentChunkEmbedding;

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

        public Builder imageEmbedding(DataPipelineDestinationImageEmbedding value) {
            this.imageEmbedding = value;
            return this;
        }

        public Builder imageTextEmbedding(DataPipelineDestinationImageTextEmbedding value) {
            this.imageTextEmbedding = value;
            return this;
        }

        public Builder videoFrameEmbedding(DataPipelineDestinationVideoFrameEmbedding value) {
            this.videoFrameEmbedding = value;
            return this;
        }

        public Builder videoTextEmbedding(DataPipelineDestinationVideoTextEmbedding value) {
            this.videoTextEmbedding = value;
            return this;
        }

        public Builder documentChunkEmbedding(DataPipelineDestinationDocumentChunkEmbedding value) {
            this.documentChunkEmbedding = value;
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
            this.imageEmbedding = from.imageEmbedding;
            this.imageTextEmbedding = from.imageTextEmbedding;
            this.videoFrameEmbedding = from.videoFrameEmbedding;
            this.videoTextEmbedding = from.videoTextEmbedding;
            this.documentChunkEmbedding = from.documentChunkEmbedding;
        }

        public DataPipelineDestination build() {
            return new DataPipelineDestination(this);
        }
    }
}
