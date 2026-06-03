package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "RouteRule")
public final class RouteRule {

    @JsonProperty("Type")
    @JacksonXmlProperty(localName = "Type")
    private String type;

    @JsonProperty("AutoCreateDataset")
    @JacksonXmlProperty(localName = "AutoCreateDataset")
    private String autoCreateDataset;

    @JsonProperty("OSSTagKey")
    @JacksonXmlProperty(localName = "OSSTagKey")
    private String ossTagKey;

    public RouteRule() {}

    private RouteRule(Builder builder) {
        this.type = builder.type;
        this.autoCreateDataset = builder.autoCreateDataset;
        this.ossTagKey = builder.ossTagKey;
    }

    public String type() { return this.type; }
    public String autoCreateDataset() { return this.autoCreateDataset; }
    public String ossTagKey() { return this.ossTagKey; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String type;
        private String autoCreateDataset;
        private String ossTagKey;

        public Builder type(String value) { this.type = value; return this; }
        public Builder autoCreateDataset(String value) { this.autoCreateDataset = value; return this; }
        public Builder ossTagKey(String value) { this.ossTagKey = value; return this; }

        private Builder() {}
        private Builder(RouteRule from) {
            this.type = from.type;
            this.autoCreateDataset = from.autoCreateDataset;
            this.ossTagKey = from.ossTagKey;
        }

        public RouteRule build() { return new RouteRule(this); }
    }
}
