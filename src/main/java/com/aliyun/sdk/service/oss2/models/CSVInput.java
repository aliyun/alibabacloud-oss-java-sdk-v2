package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the CSV input configuration.
 */
public final class CSVInput {
    @JacksonXmlProperty(localName = "FileHeaderInfo")
    private String fileHeaderInfo;

    @JacksonXmlProperty(localName = "RecordDelimiter")
    private String recordDelimiter;

    @JacksonXmlProperty(localName = "FieldDelimiter")
    private String fieldDelimiter;

    @JacksonXmlProperty(localName = "QuoteCharacter")
    private String quoteCharacter;

    @JacksonXmlProperty(localName = "CommentCharacter")
    private String commentCharacter;

    @JacksonXmlProperty(localName = "Range")
    private String range;

    @JacksonXmlProperty(localName = "AllowQuotedRecordDelimiter")
    private Boolean allowQuotedRecordDelimiter;

    public CSVInput() {
    }

    private CSVInput(Builder builder) {
        this.fileHeaderInfo = builder.fileHeaderInfo;
        this.recordDelimiter = builder.recordDelimiter;
        this.fieldDelimiter = builder.fieldDelimiter;
        this.quoteCharacter = builder.quoteCharacter;
        this.commentCharacter = builder.commentCharacter;
        this.range = builder.range;
        this.allowQuotedRecordDelimiter = builder.allowQuotedRecordDelimiter;
    }

    public String fileHeaderInfo() {
        return this.fileHeaderInfo;
    }

    public String recordDelimiter() {
        return this.recordDelimiter;
    }

    public String fieldDelimiter() {
        return this.fieldDelimiter;
    }

    public String quoteCharacter() {
        return this.quoteCharacter;
    }

    public String commentCharacter() {
        return this.commentCharacter;
    }

    public String range() {
        return this.range;
    }

    public Boolean allowQuotedRecordDelimiter() {
        return this.allowQuotedRecordDelimiter;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String fileHeaderInfo;
        private String recordDelimiter;
        private String fieldDelimiter;
        private String quoteCharacter;
        private String commentCharacter;
        private String range;
        private Boolean allowQuotedRecordDelimiter;

        private Builder() {
        }

        private Builder(CSVInput from) {
            this.fileHeaderInfo = from.fileHeaderInfo;
            this.recordDelimiter = from.recordDelimiter;
            this.fieldDelimiter = from.fieldDelimiter;
            this.quoteCharacter = from.quoteCharacter;
            this.commentCharacter = from.commentCharacter;
            this.range = from.range;
            this.allowQuotedRecordDelimiter = from.allowQuotedRecordDelimiter;
        }

        public Builder fileHeaderInfo(String value) {
            this.fileHeaderInfo = value;
            return this;
        }

        public Builder recordDelimiter(String value) {
            this.recordDelimiter = value;
            return this;
        }

        public Builder fieldDelimiter(String value) {
            this.fieldDelimiter = value;
            return this;
        }

        public Builder quoteCharacter(String value) {
            this.quoteCharacter = value;
            return this;
        }

        public Builder commentCharacter(String value) {
            this.commentCharacter = value;
            return this;
        }

        public Builder range(String value) {
            this.range = value;
            return this;
        }

        public Builder allowQuotedRecordDelimiter(Boolean value) {
            this.allowQuotedRecordDelimiter = value;
            return this;
        }

        public CSVInput build() {
            return new CSVInput(this);
        }
    }
}
