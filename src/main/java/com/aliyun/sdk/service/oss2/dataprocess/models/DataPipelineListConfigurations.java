package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Result of listing data pipeline configurations.
 */
@JacksonXmlRootElement(localName = "ListDataPipelineConfigurationsResult")
public final class DataPipelineListConfigurations {
    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlElementWrapper(localName = "DataPipelineConfigurations")
    @JacksonXmlProperty(localName = "DataPipelineConfiguration")
    private List<DataPipelineConfiguration> dataPipelineConfigurations;

    public DataPipelineListConfigurations() {
    }

    private DataPipelineListConfigurations(Builder builder) {
        this.nextToken = builder.nextToken;
        this.dataPipelineConfigurations = builder.dataPipelineConfigurations;
    }

    public String nextToken() {
        return nextToken;
    }

    public List<DataPipelineConfiguration> dataPipelineConfigurations() {
        return dataPipelineConfigurations;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String nextToken;
        private List<DataPipelineConfiguration> dataPipelineConfigurations;

        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }

        public Builder dataPipelineConfigurations(List<DataPipelineConfiguration> value) {
            this.dataPipelineConfigurations = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineListConfigurations from) {
            this.nextToken = from.nextToken;
            this.dataPipelineConfigurations = from.dataPipelineConfigurations;
        }

        public DataPipelineListConfigurations build() {
            return new DataPipelineListConfigurations(this);
        }
    }
}
