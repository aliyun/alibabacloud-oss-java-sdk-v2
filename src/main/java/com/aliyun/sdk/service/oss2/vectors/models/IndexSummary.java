package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The summary of a vector index.
 */
public class IndexSummary {
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("indexName")
    private String indexName;
    @JsonProperty("dataType")
    private String dataType;
    @JsonProperty("dimension")
    private Integer dimension;
    @JsonProperty("distanceMetric")
    private String distanceMetric;
    @JsonProperty("metadata")
    private Map<String, Object> metadata;
    @JsonProperty("vectorBucketName")
    private String vectorBucketName;
    @JsonProperty("status")
    private String status;

    public IndexSummary() {
    }

    private IndexSummary(Builder builder) {
        this.createTime = builder.createTime;
        this.indexName = builder.indexName;
        this.dataType = builder.dataType;
        this.dimension = builder.dimension;
        this.distanceMetric = builder.distanceMetric;
        this.metadata = builder.metadata;
        this.vectorBucketName = builder.vectorBucketName;
        this.status = builder.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String createTime() {
        return createTime;
    }

    public String indexName() {
        return indexName;
    }

    public String dataType() {
        return dataType;
    }

    public Integer dimension() {
        return dimension;
    }

    public String distanceMetric() {
        return distanceMetric;
    }

    public Map<String, Object> metadata() {
        return metadata;
    }

    public String vectorBucketName() {
        return vectorBucketName;
    }

    public String status() {
        return status;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String createTime;
        private String indexName;
        private String dataType;
        private Integer dimension;
        private String distanceMetric;
        private Map<String, Object> metadata;
        private String vectorBucketName;
        private String status;

        private Builder() {
        }

        private Builder(IndexSummary from) {
            this.createTime = from.createTime;
            this.indexName = from.indexName;
            this.dataType = from.dataType;
            this.dimension = from.dimension;
            this.distanceMetric = from.distanceMetric;
            this.metadata = from.metadata;
            this.vectorBucketName = from.vectorBucketName;
            this.status = from.status;
        }

        public Builder createTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder indexName(String indexName) {
            this.indexName = indexName;
            return this;
        }

        public Builder dataType(String dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder dimension(Integer dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder distanceMetric(String distanceMetric) {
            this.distanceMetric = distanceMetric;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder vectorBucketName(String vectorBucketName) {
            this.vectorBucketName = vectorBucketName;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public IndexSummary build() {
            return new IndexSummary(this);
        }
    }
}
