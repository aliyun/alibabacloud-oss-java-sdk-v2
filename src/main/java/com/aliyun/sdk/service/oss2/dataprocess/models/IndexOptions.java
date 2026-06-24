package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "IndexOptions")
public final class IndexOptions {

    @JsonProperty("IgnoreObjectDelete")
    @JacksonXmlProperty(localName = "IgnoreObjectDelete")
    private String ignoreObjectDelete;

    @JsonProperty("IgnoreEvents")
    @JacksonXmlElementWrapper(localName = "IgnoreEvents")
    @JacksonXmlProperty(localName = "IgnoreEvent")
    private List<String> ignoreEvents;

    public IndexOptions() {}

    private IndexOptions(Builder builder) {
        this.ignoreObjectDelete = builder.ignoreObjectDelete;
        this.ignoreEvents = builder.ignoreEvents;
    }

    public String ignoreObjectDelete() { return this.ignoreObjectDelete; }
    public List<String> ignoreEvents() { return this.ignoreEvents; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String ignoreObjectDelete;
        private List<String> ignoreEvents;

        public Builder ignoreObjectDelete(String value) { this.ignoreObjectDelete = value; return this; }
        public Builder ignoreObjectDelete(Boolean value) { this.ignoreObjectDelete = value != null ? value.toString() : null; return this; }
        public Builder ignoreEvents(List<String> value) { this.ignoreEvents = value; return this; }

        private Builder() {}
        private Builder(IndexOptions from) {
            this.ignoreObjectDelete = from.ignoreObjectDelete;
            this.ignoreEvents = from.ignoreEvents;
        }

        public IndexOptions build() { return new IndexOptions(this); }
    }
}
