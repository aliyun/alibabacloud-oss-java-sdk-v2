package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Image and video insight settings for a V2 data pipeline.
 */
@JacksonXmlRootElement(localName = "Insights")
public final class DataPipelineInsights {
    @JacksonXmlProperty(localName = "Image")
    private InsightsImage image;

    @JacksonXmlProperty(localName = "Video")
    private InsightsVideo video;

    public DataPipelineInsights() {
    }

    private DataPipelineInsights(Builder builder) {
        this.image = builder.image;
        this.video = builder.video;
    }

    public InsightsImage image() {
        return image;
    }

    public InsightsVideo video() {
        return video;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private InsightsImage image;
        private InsightsVideo video;

        public Builder image(InsightsImage value) {
            this.image = value;
            return this;
        }

        public Builder video(InsightsVideo value) {
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
