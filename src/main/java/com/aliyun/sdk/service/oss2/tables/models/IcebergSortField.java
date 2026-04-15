package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcebergSortField {

    @JsonProperty("sourceId")
    private Integer sourceId;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("nullOrder")
    private String nullOrder;

    @JsonProperty("transform")
    private String transform;

    public IcebergSortField() {
    }

    private IcebergSortField(Builder builder) {
        this.sourceId = builder.sourceId;
        this.direction = builder.direction;
        this.nullOrder = builder.nullOrder;
        this.transform = builder.transform;
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

    public String direction() {
        return direction;
    }

    public String nullOrder() {
        return nullOrder;
    }

    public String transform() {
        return transform;
    }

    public static class Builder {
        private Integer sourceId;
        private String direction;
        private String nullOrder;
        private String transform;

        private Builder() {
        }

        private Builder(IcebergSortField model) {
            this.sourceId = model.sourceId;
            this.direction = model.direction;
            this.nullOrder = model.nullOrder;
            this.transform = model.transform;
        }

        public Builder sourceId(Integer sourceId) {
            this.sourceId = sourceId;
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

        public Builder transform(String transform) {
            this.transform = transform;
            return this;
        }

        public IcebergSortField build() {
            return new IcebergSortField(this);
        }
    }
}
