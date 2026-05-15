package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The container that stores the select meta status.
 */
@JacksonXmlRootElement(localName = "SelectMetaStatus")
public final class SelectMetaStatus {
    @JacksonXmlProperty(localName = "ColsCount")
    private Long colsCount;

    @JacksonXmlProperty(localName = "ErrorMessage")
    private String errorMessage;

    @JacksonXmlProperty(localName = "Offset")
    private Long offset;

    @JacksonXmlProperty(localName = "RowsCount")
    private Long rowsCount;

    @JacksonXmlProperty(localName = "SplitsCount")
    private Long splitsCount;

    @JacksonXmlProperty(localName = "Status")
    private Long status;

    @JacksonXmlProperty(localName = "TotalScannedBytes")
    private Long totalScannedBytes;

    public SelectMetaStatus() {
    }

    private SelectMetaStatus(Builder builder) {
        this.colsCount = builder.colsCount;
        this.errorMessage = builder.errorMessage;
        this.offset = builder.offset;
        this.rowsCount = builder.rowsCount;
        this.splitsCount = builder.splitsCount;
        this.status = builder.status;
        this.totalScannedBytes = builder.totalScannedBytes;
    }

    public Long colsCount() {
        return this.colsCount;
    }

    public String errorMessage() {
        return this.errorMessage;
    }

    public Long offset() {
        return this.offset;
    }

    public Long rowsCount() {
        return this.rowsCount;
    }

    public Long splitsCount() {
        return this.splitsCount;
    }

    public Long status() {
        return this.status;
    }

    public Long totalScannedBytes() {
        return this.totalScannedBytes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long colsCount;
        private String errorMessage;
        private Long offset;
        private Long rowsCount;
        private Long splitsCount;
        private Long status;
        private Long totalScannedBytes;

        private Builder() {
        }

        private Builder(SelectMetaStatus from) {
            this.colsCount = from.colsCount;
            this.errorMessage = from.errorMessage;
            this.offset = from.offset;
            this.rowsCount = from.rowsCount;
            this.splitsCount = from.splitsCount;
            this.status = from.status;
            this.totalScannedBytes = from.totalScannedBytes;
        }

        public Builder colsCount(Long value) {
            this.colsCount = value;
            return this;
        }

        public Builder errorMessage(String value) {
            this.errorMessage = value;
            return this;
        }

        public Builder offset(Long value) {
            this.offset = value;
            return this;
        }

        public Builder rowsCount(Long value) {
            this.rowsCount = value;
            return this;
        }

        public Builder splitsCount(Long value) {
            this.splitsCount = value;
            return this;
        }

        public Builder status(Long value) {
            this.status = value;
            return this;
        }

        public Builder totalScannedBytes(Long value) {
            this.totalScannedBytes = value;
            return this;
        }

        public SelectMetaStatus build() {
            return new SelectMetaStatus(this);
        }
    }
}
