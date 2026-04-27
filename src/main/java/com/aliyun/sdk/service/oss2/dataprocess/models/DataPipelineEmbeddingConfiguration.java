package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Embedding configuration for data pipeline.
 */
@JacksonXmlRootElement(localName = "DataPipelineEmbeddingConfiguration")
public final class DataPipelineEmbeddingConfiguration {
    @JacksonXmlProperty(localName = "EmbeddingProvider")
    private String embeddingProvider;

    @JacksonXmlProperty(localName = "ApiKey")
    private String apiKey;

    @JacksonXmlProperty(localName = "Model")
    private String model;

    @JacksonXmlProperty(localName = "FPS")
    private Float fps;

    public DataPipelineEmbeddingConfiguration() {
    }

    private DataPipelineEmbeddingConfiguration(Builder builder) {
        this.embeddingProvider = builder.embeddingProvider;
        this.apiKey = builder.apiKey;
        this.model = builder.model;
        this.fps = builder.fps;
    }

    public String embeddingProvider() {
        return embeddingProvider;
    }

    public String apiKey() {
        return apiKey;
    }

    public String model() {
        return model;
    }

    public Float fps() {
        return fps;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String embeddingProvider;
        private String apiKey;
        private String model;
        private Float fps;

        public Builder embeddingProvider(String value) {
            this.embeddingProvider = value;
            return this;
        }

        public Builder apiKey(String value) {
            this.apiKey = value;
            return this;
        }

        public Builder model(String value) {
            this.model = value;
            return this;
        }

        public Builder fps(Float value) {
            this.fps = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineEmbeddingConfiguration from) {
            this.embeddingProvider = from.embeddingProvider;
            this.apiKey = from.apiKey;
            this.model = from.model;
            this.fps = from.fps;
        }

        public DataPipelineEmbeddingConfiguration build() {
            return new DataPipelineEmbeddingConfiguration(this);
        }
    }
}
