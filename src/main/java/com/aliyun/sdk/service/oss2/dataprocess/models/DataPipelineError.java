package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Error configuration for data pipeline.
 */
@JacksonXmlRootElement(localName = "DataPipelineError")
public final class DataPipelineError {
    @JacksonXmlProperty(localName = "ErrorMode")
    private String errorMode;

    @JacksonXmlProperty(localName = "ErrorBucket")
    private String errorBucket;

    @JacksonXmlProperty(localName = "ErrorPrefix")
    private String errorPrefix;

    public DataPipelineError() {
    }

    private DataPipelineError(Builder builder) {
        this.errorMode = builder.errorMode;
        this.errorBucket = builder.errorBucket;
        this.errorPrefix = builder.errorPrefix;
    }

    public String errorMode() {
        return errorMode;
    }

    public String errorBucket() {
        return errorBucket;
    }

    public String errorPrefix() {
        return errorPrefix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String errorMode;
        private String errorBucket;
        private String errorPrefix;

        public Builder errorMode(String value) {
            this.errorMode = value;
            return this;
        }

        public Builder errorBucket(String value) {
            this.errorBucket = value;
            return this;
        }

        public Builder errorPrefix(String value) {
            this.errorPrefix = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineError from) {
            this.errorMode = from.errorMode;
            this.errorBucket = from.errorBucket;
            this.errorPrefix = from.errorPrefix;
        }

        public DataPipelineError build() {
            return new DataPipelineError(this);
        }
    }
}
