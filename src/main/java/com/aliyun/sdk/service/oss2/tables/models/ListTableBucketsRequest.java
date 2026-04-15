package com.aliyun.sdk.service.oss2.tables.models;

public final class ListTableBucketsRequest extends TableRequestModel {

    private ListTableBucketsRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String prefix() {
        return this.parameters.get("prefix");
    }

    public String continuationToken() {
        return this.parameters.get("continuationToken");
    }

    public String maxBuckets() {
        return this.parameters.get("maxBuckets");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListTableBucketsRequest from) {
            super(from);
        }

        public Builder prefix(String value) {
            this.parameters.put("prefix", value);
            return this;
        }

        public Builder continuationToken(String value) {
            this.parameters.put("continuationToken", value);
            return this;
        }

        public Builder maxBuckets(Integer value) {
            this.parameters.put("maxBuckets", value != null ? value.toString() : null);
            return this;
        }

        public ListTableBucketsRequest build() {
            return new ListTableBucketsRequest(this);
        }
    }
}
