package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the JSON output configuration.
 */
public final class JSONOutput {
    @JacksonXmlProperty(localName = "RecordDelimiter")
    private String recordDelimiter;

    public JSONOutput() {
    }

    private JSONOutput(Builder builder) {
        this.recordDelimiter = builder.recordDelimiter;
    }

    public String recordDelimiter() {
        return this.recordDelimiter;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String recordDelimiter;

        private Builder() {
        }

        private Builder(JSONOutput from) {
            this.recordDelimiter = from.recordDelimiter;
        }

        public Builder recordDelimiter(String value) {
            this.recordDelimiter = value;
            return this;
        }

        public JSONOutput build() {
            return new JSONOutput(this);
        }
    }
}
