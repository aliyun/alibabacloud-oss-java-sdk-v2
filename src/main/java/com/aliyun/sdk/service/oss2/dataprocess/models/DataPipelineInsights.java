package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Image and video insight settings for a V2 data pipeline.
 */
@JacksonXmlRootElement(localName = "Insights")
public final class DataPipelineInsights {
    @JacksonXmlProperty(localName = "Image")
    private DataPipelineInsightsImage image;

    @JacksonXmlProperty(localName = "Video")
    private DataPipelineInsightsVideo video;

    public DataPipelineInsights() {
    }

    private DataPipelineInsights(Builder builder) {
        this.image = builder.image;
        this.video = builder.video;
    }

    public DataPipelineInsightsImage image() {
        return image;
    }

    public DataPipelineInsightsVideo video() {
        return video;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private DataPipelineInsightsImage image;
        private DataPipelineInsightsVideo video;

        public Builder image(DataPipelineInsightsImage value) {
            this.image = value;
            return this;
        }

        public Builder video(DataPipelineInsightsVideo value) {
            this.video = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineInsights from) {
            this.image = from.image;
            this.video = from.video;
        }

        public DataPipelineInsights build() {
            return new DataPipelineInsights(this);
        }
    }
}
