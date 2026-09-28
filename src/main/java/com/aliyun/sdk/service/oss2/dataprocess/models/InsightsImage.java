package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Image settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "Image")
public final class InsightsImage {
    @JacksonXmlProperty(localName = "Caption")
    private InsightsCaption caption;

    public InsightsImage() {
    }

    private InsightsImage(Builder builder) {
        this.caption = builder.caption;
    }

    public InsightsCaption caption() {
        return caption;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private InsightsCaption caption;

        public Builder caption(InsightsCaption value) {
            this.caption = value;
            return this;
        }

        private Builder() {
        }

        private Builder(InsightsImage from) {
            this.caption = from.caption;
        }

        public InsightsImage build() {
            return new InsightsImage(this);
        }
    }
}
