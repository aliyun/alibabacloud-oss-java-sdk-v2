package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Data pipeline configuration.
 */
@JacksonXmlRootElement(localName = "DataPipelineConfiguration")
public final class DataPipelineConfiguration {
    @JacksonXmlProperty(localName = "DataPipelineName")
    private String dataPipelineName;

    @JacksonXmlProperty(localName = "DataPipelineDescription")
    private String dataPipelineDescription;

    @JacksonXmlProperty(localName = "DataPipelineRole")
    private String dataPipelineRole;

    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "DataPipelineEmbeddingConfiguration")
    private DataPipelineEmbeddingConfiguration dataPipelineEmbeddingConfiguration;

    @JacksonXmlProperty(localName = "Destination")
    private DataPipelineDestination destination;

    @JacksonXmlProperty(localName = "DataPipelineError")
    private DataPipelineError dataPipelineError;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Sources")
    private List<DataPipelineSource> sources;

    public DataPipelineConfiguration() {
    }

    private DataPipelineConfiguration(Builder builder) {
        this.dataPipelineName = builder.dataPipelineName;
        this.dataPipelineDescription = builder.dataPipelineDescription;
        this.dataPipelineRole = builder.dataPipelineRole;
        this.status = builder.status;
        this.dataPipelineEmbeddingConfiguration = builder.dataPipelineEmbeddingConfiguration;
        this.destination = builder.destination;
        this.dataPipelineError = builder.dataPipelineError;
        this.createTime = builder.createTime;
        this.sources = builder.sources;
    }

    public String dataPipelineName() {
        return dataPipelineName;
    }

    public String dataPipelineDescription() {
        return dataPipelineDescription;
    }

    public String dataPipelineRole() {
        return dataPipelineRole;
    }

    public String status() {
        return status;
    }

    public DataPipelineEmbeddingConfiguration dataPipelineEmbeddingConfiguration() {
        return dataPipelineEmbeddingConfiguration;
    }

    public DataPipelineDestination destination() {
        return destination;
    }

    public DataPipelineError dataPipelineError() {
        return dataPipelineError;
    }

    public String createTime() {
        return createTime;
    }

    public List<DataPipelineSource> sources() {
        return sources;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String dataPipelineName;
        private String dataPipelineDescription;
        private String dataPipelineRole;
        private String status;
        private DataPipelineEmbeddingConfiguration dataPipelineEmbeddingConfiguration;
        private DataPipelineDestination destination;
        private DataPipelineError dataPipelineError;
        private String createTime;
        private List<DataPipelineSource> sources;

        public Builder dataPipelineName(String value) {
            this.dataPipelineName = value;
            return this;
        }

        public Builder dataPipelineDescription(String value) {
            this.dataPipelineDescription = value;
            return this;
        }

        public Builder dataPipelineRole(String value) {
            this.dataPipelineRole = value;
            return this;
        }

        public Builder status(String value) {
            this.status = value;
            return this;
        }

        public Builder dataPipelineEmbeddingConfiguration(DataPipelineEmbeddingConfiguration value) {
            this.dataPipelineEmbeddingConfiguration = value;
            return this;
        }

        public Builder destination(DataPipelineDestination value) {
            this.destination = value;
            return this;
        }

        public Builder dataPipelineError(DataPipelineError value) {
            this.dataPipelineError = value;
            return this;
        }

        public Builder createTime(String value) {
            this.createTime = value;
            return this;
        }

        public Builder sources(List<DataPipelineSource> value) {
            this.sources = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineConfiguration from) {
            this.dataPipelineName = from.dataPipelineName;
            this.dataPipelineDescription = from.dataPipelineDescription;
            this.dataPipelineRole = from.dataPipelineRole;
            this.status = from.status;
            this.dataPipelineEmbeddingConfiguration = from.dataPipelineEmbeddingConfiguration;
            this.destination = from.destination;
            this.dataPipelineError = from.dataPipelineError;
            this.createTime = from.createTime;
            this.sources = from.sources;
        }

        public DataPipelineConfiguration build() {
            return new DataPipelineConfiguration(this);
        }
    }
}
