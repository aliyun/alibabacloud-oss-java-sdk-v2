package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Aggregation")
public final class Aggregation {

    @JacksonXmlProperty(localName = "Field")
    private String field;

    @JacksonXmlProperty(localName = "Operation")
    private String operation;

    public Aggregation() {}

    private Aggregation(Builder builder) {
        this.field = builder.field;
        this.operation = builder.operation;
    }

    public String field() { return this.field; }
    public String operation() { return this.operation; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String field;
        private String operation;

        public Builder field(String value) { this.field = value; return this; }
        public Builder operation(String value) { this.operation = value; return this; }

        private Builder() { super(); }

        private Builder(Aggregation from) {
            this.field = from.field;
            this.operation = from.operation;
        }

        public Aggregation build() { return new Aggregation(this); }
    }
}
