package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableBucketSummary {

    @JsonProperty("arn")
    private String arn;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ownerAccountId")
    private String ownerAccountId;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("tableBucketId")
    private String tableBucketId;

    @JsonProperty("type")
    private String type;

    public TableBucketSummary() {
    }

    private TableBucketSummary(Builder builder) {
        this.arn = builder.arn;
        this.name = builder.name;
        this.ownerAccountId = builder.ownerAccountId;
        this.createdAt = builder.createdAt;
        this.tableBucketId = builder.tableBucketId;
        this.type = builder.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String arn() {
        return arn;
    }

    public String name() {
        return name;
    }

    public String ownerAccountId() {
        return ownerAccountId;
    }

    public String createdAt() {
        return createdAt;
    }

    public String tableBucketId() {
        return tableBucketId;
    }

    public String type() {
        return type;
    }

    public static class Builder {
        private String arn;
        private String name;
        private String ownerAccountId;
        private String createdAt;
        private String tableBucketId;
        private String type;

        private Builder() {
        }

        private Builder(TableBucketSummary model) {
            this.arn = model.arn;
            this.name = model.name;
            this.ownerAccountId = model.ownerAccountId;
            this.createdAt = model.createdAt;
            this.tableBucketId = model.tableBucketId;
            this.type = model.type;
        }

        public Builder arn(String arn) {
            this.arn = arn;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ownerAccountId(String ownerAccountId) {
            this.ownerAccountId = ownerAccountId;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder tableBucketId(String tableBucketId) {
            this.tableBucketId = tableBucketId;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public TableBucketSummary build() {
            return new TableBucketSummary(this);
        }
    }
}