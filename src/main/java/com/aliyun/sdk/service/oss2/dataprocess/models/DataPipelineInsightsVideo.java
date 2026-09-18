package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Video settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "Video")
public final class DataPipelineInsightsVideo {
    @JacksonXmlProperty(localName = "Caption")
    private DataPipelineInsightsCaption caption;

    @JacksonXmlProperty(localName = "FrameEmbedding")
    private DataPipelineInsightsFrameEmbedding frameEmbedding;

    public DataPipelineInsightsVideo() {
    }

    private DataPipelineInsightsVideo(Builder builder) {
        this.caption = builder.caption;
        this.frameEmbedding = builder.frameEmbedding;
    }

    public DataPipelineInsightsCaption caption() {
        return caption;
    }

    public DataPipelineInsightsFrameEmbedding frameEmbedding() {
        return frameEmbedding;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private DataPipelineInsightsCaption caption;
        private DataPipelineInsightsFrameEmbedding frameEmbedding;

        public Builder caption(DataPipelineInsightsCaption value) {
            this.caption = value;
            return this;
        }

        public Builder frameEmbedding(DataPipelineInsightsFrameEmbedding value) {
            this.frameEmbedding = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineInsightsVideo from) {
            this.caption = from.caption;
            this.frameEmbedding = from.frameEmbedding;
        }

        public DataPipelineInsightsVideo build() {
            return new DataPipelineInsightsVideo(this);
        }
    }
}
