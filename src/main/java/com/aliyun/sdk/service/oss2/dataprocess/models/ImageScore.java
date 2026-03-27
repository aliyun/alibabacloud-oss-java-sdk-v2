package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ImageScore")
public final class ImageScore {

    @JacksonXmlProperty(localName = "OverallQualityScore")
    private Float overallQualityScore;

    public ImageScore() {
    }

    private ImageScore(Builder builder) {
        this.overallQualityScore = builder.overallQualityScore;
    }

    public Float overallQualityScore() {
        return this.overallQualityScore;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Float overallQualityScore;

        public Builder overallQualityScore(Float value) {
            this.overallQualityScore = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(ImageScore from) {
            this.overallQualityScore = from.overallQualityScore;
        }

        public ImageScore build() {
            return new ImageScore(this);
        }
    }
}
