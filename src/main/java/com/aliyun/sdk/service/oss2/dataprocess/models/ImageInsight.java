package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "ImageInsight")
public final class ImageInsight {

    @JacksonXmlProperty(localName = "Caption")
    private String caption;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlElementWrapper(localName = "MultilingualContent")
    @JacksonXmlProperty(localName = "Content")
    private List<MultilingualContent> multilingualContent;

    public ImageInsight() {}

    private ImageInsight(Builder builder) {
        this.caption = builder.caption;
        this.description = builder.description;
        this.multilingualContent = builder.multilingualContent;
    }

    public String caption() { return this.caption; }
    public String description() { return this.description; }
    public List<MultilingualContent> multilingualContent() { return this.multilingualContent; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String caption;
        private String description;
        private List<MultilingualContent> multilingualContent;

        public Builder caption(String value) { this.caption = value; return this; }
        public Builder description(String value) { this.description = value; return this; }
        public Builder multilingualContent(List<MultilingualContent> value) { this.multilingualContent = value; return this; }

        private Builder() { super(); }

        private Builder(ImageInsight from) {
            this.caption = from.caption;
            this.description = from.description;
            this.multilingualContent = from.multilingualContent;
        }

        public ImageInsight build() { return new ImageInsight(this); }
    }
}
