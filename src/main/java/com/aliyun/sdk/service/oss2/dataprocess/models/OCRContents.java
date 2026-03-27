package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "OCRContents")
public final class OCRContents {

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "Contents")
    private String contents;

    @JacksonXmlProperty(localName = "Confidence")
    private Float confidence;

    @JacksonXmlProperty(localName = "Boundary")
    private Boundary boundary;

    public OCRContents() {
    }

    private OCRContents(Builder builder) {
        this.language = builder.language;
        this.contents = builder.contents;
        this.confidence = builder.confidence;
        this.boundary = builder.boundary;
    }

    public String language() {
        return this.language;
    }

    public String contents() {
        return this.contents;
    }

    public Float confidence() {
        return this.confidence;
    }

    public Boundary boundary() {
        return this.boundary;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String language;
        private String contents;
        private Float confidence;
        private Boundary boundary;

        public Builder language(String value) {
            this.language = value;
            return this;
        }

        public Builder contents(String value) {
            this.contents = value;
            return this;
        }

        public Builder confidence(Float value) {
            this.confidence = value;
            return this;
        }

        public Builder boundary(Boundary value) {
            this.boundary = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(OCRContents from) {
            this.language = from.language;
            this.contents = from.contents;
            this.confidence = from.confidence;
            this.boundary = from.boundary;
        }

        public OCRContents build() {
            return new OCRContents(this);
        }
    }
}
