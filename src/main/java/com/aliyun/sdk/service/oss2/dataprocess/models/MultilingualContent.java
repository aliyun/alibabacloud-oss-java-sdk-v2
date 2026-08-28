package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Content")
public final class MultilingualContent {

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "Caption")
    private String caption;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    public MultilingualContent() {}

    private MultilingualContent(Builder builder) {
        this.language = builder.language;
        this.caption = builder.caption;
        this.description = builder.description;
    }

    public String language() { return this.language; }
    public String caption() { return this.caption; }
    public String description() { return this.description; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String language;
        private String caption;
        private String description;

        public Builder language(String value) { this.language = value; return this; }
        public Builder caption(String value) { this.caption = value; return this; }
        public Builder description(String value) { this.description = value; return this; }

        private Builder() { super(); }

        private Builder(MultilingualContent from) {
            this.language = from.language;
            this.caption = from.caption;
            this.description = from.description;
        }

        public MultilingualContent build() { return new MultilingualContent(this); }
    }
}
