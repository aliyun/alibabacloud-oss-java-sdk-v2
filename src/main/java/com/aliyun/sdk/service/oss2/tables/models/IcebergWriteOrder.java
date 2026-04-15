package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IcebergWriteOrder {

    @JsonProperty("orderId")
    private Integer orderId;

    @JsonProperty("fields")
    private List<IcebergWriteOrderField> fields;

    public IcebergWriteOrder() {
    }

    private IcebergWriteOrder(Builder builder) {
        this.orderId = builder.orderId;
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer orderId() {
        return orderId;
    }

    public List<IcebergWriteOrderField> fields() {
        return fields;
    }

    public static class Builder {
        private Integer orderId;
        private List<IcebergWriteOrderField> fields;

        private Builder() {
        }

        private Builder(IcebergWriteOrder model) {
            this.orderId = model.orderId;
            this.fields = model.fields;
        }

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder fields(List<IcebergWriteOrderField> fields) {
            this.fields = fields;
            return this;
        }

        public IcebergWriteOrder build() {
            return new IcebergWriteOrder(this);
        }
    }
}