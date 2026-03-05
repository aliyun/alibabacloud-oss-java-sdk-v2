package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

/**
 * The result for the SimpleQuery operation.
 */
public final class SimpleQueryResult extends ResultModel {
    private final String nextToken;
    private final List<File> files;
    private final List<AggregationInfo> aggregations;
    private final Long totalHits;

    SimpleQueryResult(Builder builder) {
        super(builder);
        this.nextToken = builder.nextToken;
        this.files = builder.files;
        this.aggregations = builder.aggregations;
        this.totalHits = builder.totalHits;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String nextToken() {
        return nextToken;
    }

    public List<File> files() {
        return files;
    }

    public List<AggregationInfo> aggregations() {
        return aggregations;
    }

    public Long totalHits() {
        return totalHits;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private String nextToken;
        private List<File> files;
        private List<AggregationInfo> aggregations;
        private Long totalHits;

        private Builder() {
            super();
        }

        private Builder(SimpleQueryResult result) {
            super(result);
            this.nextToken = result.nextToken;
            this.files = result.files;
            this.aggregations = result.aggregations;
            this.totalHits = result.totalHits;
        }

        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }

        public Builder files(List<File> value) {
            this.files = value;
            return this;
        }

        public Builder aggregations(List<AggregationInfo> value) {
            this.aggregations = value;
            return this;
        }

        public Builder totalHits(Long value) {
            this.totalHits = value;
            return this;
        }

        public SimpleQueryResult build() {
            return new SimpleQueryResult(this);
        }
    }
}
