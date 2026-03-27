package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "AggregationGroup")
public final class AggregationGroup {

    @JacksonXmlProperty(localName = "Value")
    private String value;

    @JacksonXmlProperty(localName = "Count")
    private Long count;

    public AggregationGroup() {}

    private AggregationGroup(Builder builder) {
        this.value = builder.value;
        this.count = builder.count;
    }

    public String value() { return this.value; }
    public Long count() { return this.count; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String value;
        private Long count;

        public Builder value(String value) { this.value = value; return this; }
        public Builder count(Long value) { this.count = value; return this; }

        private Builder() { super(); }

        private Builder(AggregationGroup from) {
            this.value = from.value;
            this.count = from.count;
        }

        public AggregationGroup build() { return new AggregationGroup(this); }
    }
}
