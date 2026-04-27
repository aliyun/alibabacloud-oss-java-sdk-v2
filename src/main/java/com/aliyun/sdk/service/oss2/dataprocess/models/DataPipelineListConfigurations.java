package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Result of listing data pipeline configurations.
 */
public final class DataPipelineListConfigurations {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DataPipelineConfiguration")
    private List<DataPipelineConfiguration> dataPipelineConfigurations;

    public DataPipelineListConfigurations() {
    }

    private DataPipelineListConfigurations(Builder builder) {
        this.dataPipelineConfigurations = builder.dataPipelineConfigurations;
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
        private List<DataPipelineConfiguration> dataPipelineConfigurations;

        public Builder dataPipelineConfigurations(List<DataPipelineConfiguration> value) {
            this.dataPipelineConfigurations = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineListConfigurations from) {
            this.dataPipelineConfigurations = from.dataPipelineConfigurations;
        }

        public DataPipelineListConfigurations build() {
            return new DataPipelineListConfigurations(this);
        }
    }
}
