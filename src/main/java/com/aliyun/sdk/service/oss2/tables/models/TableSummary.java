package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TableSummary {

    @JsonProperty("name")
    private String name;

    @JsonProperty("namespace")
    private List<String> namespace;

    @JsonProperty("tableARN")
    private String tableARN;

    @JsonProperty("type")
    private String type;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("modifiedAt")
    private String modifiedAt;

    public TableSummary() {
    }

    private TableSummary(Builder builder) {
        this.name = builder.name;
        this.namespace = builder.namespace;
        this.tableARN = builder.tableARN;
        this.type = builder.type;
        this.createdAt = builder.createdAt;
        this.modifiedAt = builder.modifiedAt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String name() {
        return name;
    }

    public List<String> namespace() {
        return namespace;
    }

    public String tableARN() {
        return tableARN;
    }

    public String type() {
        return type;
    }

    public String createdAt() {
        return createdAt;
    }

    public String modifiedAt() {
        return modifiedAt;
    }

    public static class Builder {
        private String name;
        private List<String> namespace;
        private String tableARN;
        private String type;
        private String createdAt;
        private String modifiedAt;

        private Builder() {
        }

        private Builder(TableSummary model) {
            this.name = model.name;
            this.namespace = model.namespace;
            this.tableARN = model.tableARN;
            this.type = model.type;
            this.createdAt = model.createdAt;
            this.modifiedAt = model.modifiedAt;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder namespace(List<String> namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder tableARN(String tableARN) {
            this.tableARN = tableARN;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder modifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public TableSummary build() {
            return new TableSummary(this);
        }
    }
}
