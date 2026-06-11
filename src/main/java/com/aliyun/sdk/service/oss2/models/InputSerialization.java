package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the format of the object that you want to select.
 */
public final class InputSerialization {
    @JacksonXmlProperty(localName = "CompressionType")
    private String compressionType;

    @JacksonXmlProperty(localName = "CSV")
    private CSVInput csv;

    @JacksonXmlProperty(localName = "JSON")
    private JSONInput json;

    public InputSerialization() {
    }

    private InputSerialization(Builder builder) {
        this.compressionType = builder.compressionType;
        this.csv = builder.csv;
        this.json = builder.json;
    }

    public String compressionType() {
        return this.compressionType;
    }

    public CSVInput csv() {
        return this.csv;
    }

    public JSONInput json() {
        return this.json;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String compressionType;
        private CSVInput csv;
        private JSONInput json;

        private Builder() {
        }

        private Builder(InputSerialization from) {
            this.compressionType = from.compressionType;
            this.csv = from.csv;
            this.json = from.json;
        }

        public Builder compressionType(String value) {
            this.compressionType = value;
            return this;
        }

        public Builder csv(CSVInput value) {
            this.csv = value;
            return this;
        }

        public Builder json(JSONInput value) {
            this.json = value;
            return this;
        }

        public InputSerialization build() {
            return new InputSerialization(this);
        }
    }
}
