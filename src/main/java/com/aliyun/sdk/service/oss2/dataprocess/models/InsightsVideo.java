package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Video settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "Video")
public final class InsightsVideo {
    @JacksonXmlProperty(localName = "Caption")
    private InsightsCaption caption;

    @JacksonXmlProperty(localName = "FrameEmbedding")
    private InsightsFrameEmbedding frameEmbedding;

    public InsightsVideo() {
    }

    private InsightsVideo(Builder builder) {
        this.caption = builder.caption;
        this.frameEmbedding = builder.frameEmbedding;
    }

    public InsightsCaption caption() {
        return caption;
    }

    public InsightsFrameEmbedding frameEmbedding() {
        return frameEmbedding;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private InsightsCaption caption;
        private InsightsFrameEmbedding frameEmbedding;

        public Builder caption(InsightsCaption value) {
            this.caption = value;
            return this;
        }

        public Builder frameEmbedding(InsightsFrameEmbedding value) {
            this.frameEmbedding = value;
            return this;
        }

        private Builder() {
        }

        private Builder(InsightsVideo from) {
            this.caption = from.caption;
            this.frameEmbedding = from.frameEmbedding;
        }

        public InsightsVideo build() {
            return new InsightsVideo(this);
        }
    }
}
