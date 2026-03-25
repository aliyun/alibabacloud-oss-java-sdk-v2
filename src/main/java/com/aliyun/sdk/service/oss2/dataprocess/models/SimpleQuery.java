package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "SimpleQuery")
public final class SimpleQuery {

    @JsonProperty("Field")
    @JacksonXmlProperty(localName = "Field")
    private String field;

    @JsonProperty("Value")
    @JacksonXmlProperty(localName = "Value")
    private String value;

    @JsonProperty("Operation")
    @JacksonXmlProperty(localName = "Operation")
    private String operation;

    @JsonProperty("SubQueries")
    @JacksonXmlElementWrapper(localName = "SubQueries")
    @JacksonXmlProperty(localName = "SimpleQuery")
    private List<SimpleQuery> subQueries;

    public SimpleQuery() {}

    private SimpleQuery(Builder builder) {
        this.field = builder.field;
        this.value = builder.value;
        this.operation = builder.operation;
        this.subQueries = builder.subQueries;
    }

    public String field() { return this.field; }
    public String value() { return this.value; }
    public String operation() { return this.operation; }
    public List<SimpleQuery> subQueries() { return this.subQueries; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String field;
        private String value;
        private String operation;
        private List<SimpleQuery> subQueries;

        public Builder field(String value) { this.field = value; return this; }
        public Builder value(String value) { this.value = value; return this; }
        public Builder operation(String value) { this.operation = value; return this; }
        public Builder subQueries(List<SimpleQuery> value) { this.subQueries = value; return this; }

        private Builder() { super(); }

        private Builder(SimpleQuery from) {
            this.field = from.field;
            this.value = from.value;
            this.operation = from.operation;
            this.subQueries = from.subQueries;
        }

        public SimpleQuery build() { return new SimpleQuery(this); }
    }
}
