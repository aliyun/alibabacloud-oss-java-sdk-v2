package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The container that stores the SQL statement.
 */
@JacksonXmlRootElement(localName = "SelectRequest")
public final class SelectRequest {
    @JacksonXmlProperty(localName = "Expression")
    private String expression;

    @JacksonXmlProperty(localName = "InputSerialization")
    private InputSerialization inputSerialization;

    @JacksonXmlProperty(localName = "OutputSerialization")
    private OutputSerialization outputSerialization;

    @JacksonXmlProperty(localName = "Options")
    private SelectRequestOptions options;

    public SelectRequest() {
    }

    private SelectRequest(Builder builder) {
        this.expression = builder.expression;
        this.inputSerialization = builder.inputSerialization;
        this.outputSerialization = builder.outputSerialization;
        this.options = builder.options;
    }

    public String expression() {
        return this.expression;
    }

    public InputSerialization inputSerialization() {
        return this.inputSerialization;
    }

    public OutputSerialization outputSerialization() {
        return this.outputSerialization;
    }

    public SelectRequestOptions options() {
        return this.options;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String expression;
        private InputSerialization inputSerialization;
        private OutputSerialization outputSerialization;
        private SelectRequestOptions options;

        private Builder() {
        }

        private Builder(SelectRequest from) {
            this.expression = from.expression;
            this.inputSerialization = from.inputSerialization;
            this.outputSerialization = from.outputSerialization;
            this.options = from.options;
        }

        public Builder expression(String value) {
            this.expression = value;
            return this;
        }

        public Builder inputSerialization(InputSerialization value) {
            this.inputSerialization = value;
            return this;
        }

        public Builder outputSerialization(OutputSerialization value) {
            this.outputSerialization = value;
            return this;
        }

        public Builder options(SelectRequestOptions value) {
            this.options = value;
            return this;
        }

        public SelectRequest build() {
            return new SelectRequest(this);
        }
    }
}
