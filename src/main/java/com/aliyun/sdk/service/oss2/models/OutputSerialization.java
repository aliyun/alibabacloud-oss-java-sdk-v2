package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the format of the object that you want to return.
 */
public final class OutputSerialization {
    @JacksonXmlProperty(localName = "CSV")
    private CSVOutput csv;

    @JacksonXmlProperty(localName = "JSON")
    private JSONOutput json;

    @JacksonXmlProperty(localName = "KeepAllColumns")
    private Boolean keepAllColumns;

    @JacksonXmlProperty(localName = "OutputRawData")
    private Boolean outputRawData;

    @JacksonXmlProperty(localName = "EnablePayloadCrc")
    private Boolean enablePayloadCrc;

    @JacksonXmlProperty(localName = "OutputHeader")
    private Boolean outputHeader;

    public OutputSerialization() {
    }

    private OutputSerialization(Builder builder) {
        this.csv = builder.csv;
        this.json = builder.json;
        this.keepAllColumns = builder.keepAllColumns;
        this.outputRawData = builder.outputRawData;
        this.enablePayloadCrc = builder.enablePayloadCrc;
        this.outputHeader = builder.outputHeader;
    }

    public CSVOutput csv() {
        return this.csv;
    }

    public JSONOutput json() {
        return this.json;
    }

    public Boolean keepAllColumns() {
        return this.keepAllColumns;
    }

    public Boolean outputRawData() {
        return this.outputRawData;
    }

    public Boolean enablePayloadCrc() {
        return this.enablePayloadCrc;
    }

    public Boolean outputHeader() {
        return this.outputHeader;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private CSVOutput csv;
        private JSONOutput json;
        private Boolean keepAllColumns;
        private Boolean outputRawData;
        private Boolean enablePayloadCrc;
        private Boolean outputHeader;

        private Builder() {
        }

        private Builder(OutputSerialization from) {
            this.csv = from.csv;
            this.json = from.json;
            this.keepAllColumns = from.keepAllColumns;
            this.outputRawData = from.outputRawData;
            this.enablePayloadCrc = from.enablePayloadCrc;
            this.outputHeader = from.outputHeader;
        }

        public Builder csv(CSVOutput value) {
            this.csv = value;
            return this;
        }

        public Builder json(JSONOutput value) {
            this.json = value;
            return this;
        }

        public Builder keepAllColumns(Boolean value) {
            this.keepAllColumns = value;
            return this;
        }

        public Builder outputRawData(Boolean value) {
            this.outputRawData = value;
            return this;
        }

        public Builder enablePayloadCrc(Boolean value) {
            this.enablePayloadCrc = value;
            return this;
        }

        public Builder outputHeader(Boolean value) {
            this.outputHeader = value;
            return this;
        }

        public OutputSerialization build() {
            return new OutputSerialization(this);
        }
    }
}
