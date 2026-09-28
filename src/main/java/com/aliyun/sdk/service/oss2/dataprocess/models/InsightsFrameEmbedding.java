package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Frame embedding settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "FrameEmbedding")
public final class InsightsFrameEmbedding {
    @JacksonXmlProperty(localName = "Snapshot")
    private InsightsSnapshot snapshot;

    public InsightsFrameEmbedding() {
    }

    private InsightsFrameEmbedding(Builder builder) {
        this.snapshot = builder.snapshot;
    }

    public InsightsSnapshot snapshot() {
        return snapshot;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private InsightsSnapshot snapshot;

        public Builder snapshot(InsightsSnapshot value) {
            this.snapshot = value;
            return this;
        }

        private Builder() {
        }

        private Builder(InsightsFrameEmbedding from) {
            this.snapshot = from.snapshot;
        }

        public InsightsFrameEmbedding build() {
            return new InsightsFrameEmbedding(this);
        }
    }
}
