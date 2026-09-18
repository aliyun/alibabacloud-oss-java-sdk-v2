package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Frame embedding settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "FrameEmbedding")
public final class DataPipelineInsightsFrameEmbedding {
    @JacksonXmlProperty(localName = "Snapshot")
    private DataPipelineInsightsSnapshot snapshot;

    public DataPipelineInsightsFrameEmbedding() {
    }

    private DataPipelineInsightsFrameEmbedding(Builder builder) {
        this.snapshot = builder.snapshot;
    }

    public DataPipelineInsightsSnapshot snapshot() {
        return snapshot;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private DataPipelineInsightsSnapshot snapshot;

        public Builder snapshot(DataPipelineInsightsSnapshot value) {
            this.snapshot = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineInsightsFrameEmbedding from) {
            this.snapshot = from.snapshot;
        }

        public DataPipelineInsightsFrameEmbedding build() {
            return new DataPipelineInsightsFrameEmbedding(this);
        }
    }
}
