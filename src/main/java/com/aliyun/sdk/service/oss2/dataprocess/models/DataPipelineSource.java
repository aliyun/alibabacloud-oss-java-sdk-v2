package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Source configuration for data pipeline.
 */
@JacksonXmlRootElement(localName = "Source")
public final class DataPipelineSource {
    @JacksonXmlProperty(localName = "InputBucket")
    private String inputBucket;

    @JacksonXmlProperty(localName = "InputDataScope")
    private String inputDataScope;

    @JacksonXmlProperty(localName = "IgnoreDelete")
    private Boolean ignoreDelete;

    @JacksonXmlProperty(localName = "FilterConfiguration")
    private DataPipelineSourceFilterConfiguration filterConfiguration;

    public DataPipelineSource() {
    }

    private DataPipelineSource(Builder builder) {
        this.inputBucket = builder.inputBucket;
        this.inputDataScope = builder.inputDataScope;
        this.ignoreDelete = builder.ignoreDelete;
        this.filterConfiguration = builder.filterConfiguration;
    }

    public String inputBucket() {
        return inputBucket;
    }

    public String inputDataScope() {
        return inputDataScope;
    }

    public Boolean ignoreDelete() {
        return ignoreDelete;
    }

    public DataPipelineSourceFilterConfiguration filterConfiguration() {
        return filterConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String inputBucket;
        private String inputDataScope;
        private Boolean ignoreDelete;
        private DataPipelineSourceFilterConfiguration filterConfiguration;

        public Builder inputBucket(String value) {
            this.inputBucket = value;
            return this;
        }

        public Builder inputDataScope(String value) {
            this.inputDataScope = value;
            return this;
        }

        public Builder ignoreDelete(Boolean value) {
            this.ignoreDelete = value;
            return this;
        }

        public Builder filterConfiguration(DataPipelineSourceFilterConfiguration value) {
            this.filterConfiguration = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineSource from) {
            this.inputBucket = from.inputBucket;
            this.inputDataScope = from.inputDataScope;
            this.ignoreDelete = from.ignoreDelete;
            this.filterConfiguration = from.filterConfiguration;
        }

        public DataPipelineSource build() {
            return new DataPipelineSource(this);
        }
    }
}
