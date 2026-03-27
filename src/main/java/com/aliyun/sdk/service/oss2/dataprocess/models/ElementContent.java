package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "ElementContent")
public final class ElementContent {

    @JacksonXmlProperty(localName = "Type")
    private String type;

    @JacksonXmlProperty(localName = "Content")
    private String content;

    @JacksonXmlProperty(localName = "URL")
    private String url;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TimeRange")
    private List<Long> timeRange;

    public ElementContent() {
    }

    private ElementContent(Builder builder) {
        this.type = builder.type;
        this.content = builder.content;
        this.url = builder.url;
        this.timeRange = builder.timeRange;
    }

    public String type() {
        return this.type;
    }

    public String content() {
        return this.content;
    }

    public String url() {
        return this.url;
    }

    public List<Long> timeRange() {
        return this.timeRange;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String type;
        private String content;
        private String url;
        private List<Long> timeRange;

        public Builder type(String value) {
            this.type = value;
            return this;
        }

        public Builder content(String value) {
            this.content = value;
            return this;
        }

        public Builder url(String value) {
            this.url = value;
            return this;
        }

        public Builder timeRange(List<Long> value) {
            this.timeRange = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(ElementContent from) {
            this.type = from.type;
            this.content = from.content;
            this.url = from.url;
            this.timeRange = from.timeRange;
        }

        public ElementContent build() {
            return new ElementContent(this);
        }
    }
}
