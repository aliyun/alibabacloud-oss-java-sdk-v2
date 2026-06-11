package com.aliyun.sdk.service.oss2.models;

/**
 * The metadata from the CreateSelectObjectMeta binary frame response.
 */
public final class SelectObjectMeta {
    private Long columnsCount;
    private String errorMessage;
    private Long offset;
    private Long rowsCount;
    private Long splitsCount;
    private Long status;
    private Long totalScanned;

    public SelectObjectMeta() {
    }

    private SelectObjectMeta(Builder builder) {
        this.columnsCount = builder.columnsCount;
        this.errorMessage = builder.errorMessage;
        this.offset = builder.offset;
        this.rowsCount = builder.rowsCount;
        this.splitsCount = builder.splitsCount;
        this.status = builder.status;
        this.totalScanned = builder.totalScanned;
    }

    public Long columnsCount() {
        return this.columnsCount;
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

    public Long totalScanned() {
        return this.totalScanned;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long columnsCount;
        private String errorMessage;
        private Long offset;
        private Long rowsCount;
        private Long splitsCount;
        private Long status;
        private Long totalScanned;

        private Builder() {
        }

        private Builder(SelectObjectMeta from) {
            this.columnsCount = from.columnsCount;
            this.errorMessage = from.errorMessage;
            this.offset = from.offset;
            this.rowsCount = from.rowsCount;
            this.splitsCount = from.splitsCount;
            this.status = from.status;
            this.totalScanned = from.totalScanned;
        }

        public Builder columnsCount(Long value) {
            this.columnsCount = value;
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

        public Builder totalScanned(Long value) {
            this.totalScanned = value;
            return this;
        }

        public SelectObjectMeta build() {
            return new SelectObjectMeta(this);
        }
    }
}
