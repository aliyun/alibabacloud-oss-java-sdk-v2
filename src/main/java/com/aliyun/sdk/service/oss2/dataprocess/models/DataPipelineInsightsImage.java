package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Image settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "Image")
public final class DataPipelineInsightsImage {
    @JacksonXmlProperty(localName = "Caption")
    private DataPipelineInsightsCaption caption;

    public DataPipelineInsightsImage() {
    }

    private DataPipelineInsightsImage(Builder builder) {
        this.caption = builder.caption;
    }

    public DataPipelineInsightsCaption caption() {
        return caption;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private DataPipelineInsightsCaption caption;

        public Builder caption(DataPipelineInsightsCaption value) {
            this.caption = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineInsightsImage from) {
            this.caption = from.caption;
        }

        public DataPipelineInsightsImage build() {
            return new DataPipelineInsightsImage(this);
        }
    }
}
