package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Caption settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "Caption")
public final class DataPipelineInsightsCaption {
    @JacksonXmlProperty(localName = "Prompt")
    private String prompt;

    public DataPipelineInsightsCaption() {
    }

    private DataPipelineInsightsCaption(Builder builder) {
        this.prompt = builder.prompt;
    }

    public String prompt() {
        return prompt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String prompt;

        public Builder prompt(String value) {
            this.prompt = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineInsightsCaption from) {
            this.prompt = from.prompt;
        }

        public DataPipelineInsightsCaption build() {
            return new DataPipelineInsightsCaption(this);
        }
    }
}
