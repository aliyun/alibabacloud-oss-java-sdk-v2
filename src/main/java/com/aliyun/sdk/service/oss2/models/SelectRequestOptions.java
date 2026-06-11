package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the options of the select operation.
 */
public final class SelectRequestOptions {
    @JacksonXmlProperty(localName = "SkipPartialDataRecord")
    private Boolean skipPartialDataRecord;

    @JacksonXmlProperty(localName = "MaxSkippedRecordsAllowed")
    private Long maxSkippedRecordsAllowed;

    public SelectRequestOptions() {
    }

    private SelectRequestOptions(Builder builder) {
        this.skipPartialDataRecord = builder.skipPartialDataRecord;
        this.maxSkippedRecordsAllowed = builder.maxSkippedRecordsAllowed;
    }

    public Boolean skipPartialDataRecord() {
        return this.skipPartialDataRecord;
    }

    public Long maxSkippedRecordsAllowed() {
        return this.maxSkippedRecordsAllowed;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean skipPartialDataRecord;
        private Long maxSkippedRecordsAllowed;

        private Builder() {
        }

        private Builder(SelectRequestOptions from) {
            this.skipPartialDataRecord = from.skipPartialDataRecord;
            this.maxSkippedRecordsAllowed = from.maxSkippedRecordsAllowed;
        }

        public Builder skipPartialDataRecord(Boolean value) {
            this.skipPartialDataRecord = value;
            return this;
        }

        public Builder maxSkippedRecordsAllowed(Long value) {
            this.maxSkippedRecordsAllowed = value;
            return this;
        }

        public SelectRequestOptions build() {
            return new SelectRequestOptions(this);
        }
    }
}
