package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IcebergSortOrder {

    @JsonProperty("fields")
    private List<IcebergSortField> fields;

    public IcebergSortOrder() {
    }

    private IcebergSortOrder(Builder builder) {
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public List<IcebergSortField> fields() {
        return fields;
    }

    public static class Builder {
        private List<IcebergSortField> fields;

        private Builder() {
        }

        private Builder(IcebergSortOrder model) {
            this.fields = model.fields;
        }

        public Builder fields(List<IcebergSortField> fields) {
            this.fields = fields;
            return this;
        }

        public IcebergSortOrder build() {
            return new IcebergSortOrder(this);
        }
    }
}
