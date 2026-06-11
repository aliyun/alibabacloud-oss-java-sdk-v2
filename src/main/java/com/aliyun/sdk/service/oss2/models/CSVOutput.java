package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the CSV output configuration.
 */
public final class CSVOutput {
    @JacksonXmlProperty(localName = "RecordDelimiter")
    private String recordDelimiter;

    @JacksonXmlProperty(localName = "FieldDelimiter")
    private String fieldDelimiter;

    public CSVOutput() {
    }

    private CSVOutput(Builder builder) {
        this.recordDelimiter = builder.recordDelimiter;
        this.fieldDelimiter = builder.fieldDelimiter;
    }

    public String recordDelimiter() {
        return this.recordDelimiter;
    }

    public String fieldDelimiter() {
        return this.fieldDelimiter;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String recordDelimiter;
        private String fieldDelimiter;

        private Builder() {
        }

        private Builder(CSVOutput from) {
            this.recordDelimiter = from.recordDelimiter;
            this.fieldDelimiter = from.fieldDelimiter;
        }

        public Builder recordDelimiter(String value) {
            this.recordDelimiter = value;
            return this;
        }

        public Builder fieldDelimiter(String value) {
            this.fieldDelimiter = value;
            return this;
        }

        public CSVOutput build() {
            return new CSVOutput(this);
        }
    }
}
