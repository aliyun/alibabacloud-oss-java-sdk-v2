package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IcebergPartitionSpec {

    @JsonProperty("specId")
    private Integer specId;

    @JsonProperty("fields")
    private List<IcebergPartitionField> fields;

    public IcebergPartitionSpec() {
    }

    private IcebergPartitionSpec(Builder builder) {
        this.specId = builder.specId;
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer specId() {
        return specId;
    }

    public List<IcebergPartitionField> fields() {
        return fields;
    }

    public static class Builder {
        private Integer specId;
        private List<IcebergPartitionField> fields;

        private Builder() {
        }

        private Builder(IcebergPartitionSpec model) {
            this.specId = model.specId;
            this.fields = model.fields;
        }

        public Builder specId(Integer specId) {
            this.specId = specId;
            return this;
        }

        public Builder fields(List<IcebergPartitionField> fields) {
            this.fields = fields;
            return this;
        }

        public IcebergPartitionSpec build() {
            return new IcebergPartitionSpec(this);
        }
    }
}