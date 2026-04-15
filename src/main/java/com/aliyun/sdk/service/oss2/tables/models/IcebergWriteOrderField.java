package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcebergWriteOrderField {

    @JsonProperty("sourceId")
    private Integer sourceId;

    @JsonProperty("transform")
    private String transform;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("nullOrder")
    private String nullOrder;

    public IcebergWriteOrderField() {
    }

    private IcebergWriteOrderField(Builder builder) {
        this.sourceId = builder.sourceId;
        this.transform = builder.transform;
        this.direction = builder.direction;
        this.nullOrder = builder.nullOrder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer sourceId() {
        return sourceId;
    }

    public String transform() {
        return transform;
    }

    public String direction() {
        return direction;
    }

    public String nullOrder() {
        return nullOrder;
    }

    public static class Builder {
        private Integer sourceId;
        private String transform;
        private String direction;
        private String nullOrder;

        private Builder() {
        }

        private Builder(IcebergWriteOrderField model) {
            this.sourceId = model.sourceId;
            this.transform = model.transform;
            this.direction = model.direction;
            this.nullOrder = model.nullOrder;
        }

        public Builder sourceId(Integer sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder transform(String transform) {
            this.transform = transform;
            return this;
        }

        public Builder direction(String direction) {
            this.direction = direction;
            return this;
        }

        public Builder nullOrder(String nullOrder) {
            this.nullOrder = nullOrder;
            return this;
        }

        public IcebergWriteOrderField build() {
            return new IcebergWriteOrderField(this);
        }
    }
}