package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Vector destination for image text embeddings.
 */
@JacksonXmlRootElement(localName = "ImageTextEmbedding")
public final class DataPipelineDestinationImageTextEmbedding {
    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;

    @JacksonXmlProperty(localName = "IndexName")
    private String indexName;

    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;

    public DataPipelineDestinationImageTextEmbedding() {
    }

    private DataPipelineDestinationImageTextEmbedding(Builder builder) {
        this.bucket = builder.bucket;
        this.indexName = builder.indexName;
        this.prefix = builder.prefix;
    }

    public String bucket() {
        return bucket;
    }

    public String indexName() {
        return indexName;
    }

    public String prefix() {
        return prefix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String bucket;
        private String indexName;
        private String prefix;

        public Builder bucket(String value) {
            this.bucket = value;
            return this;
        }

        public Builder indexName(String value) {
            this.indexName = value;
            return this;
        }

        public Builder prefix(String value) {
            this.prefix = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineDestinationImageTextEmbedding from) {
            this.bucket = from.bucket;
            this.indexName = from.indexName;
            this.prefix = from.prefix;
        }

        public DataPipelineDestinationImageTextEmbedding build() {
            return new DataPipelineDestinationImageTextEmbedding(this);
        }
    }
}
