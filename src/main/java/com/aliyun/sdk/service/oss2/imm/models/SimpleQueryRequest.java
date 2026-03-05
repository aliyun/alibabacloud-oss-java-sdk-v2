package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the SimpleQuery operation.
 */
public final class SimpleQueryRequest extends RequestModel {
    private final String bucket;
    private final String datasetName;
    private final String nextToken;
    private final Integer maxResults;
    private final SimpleQuery query;
    private final String sort;
    private final String order;
    private final List<Aggregation> aggregations;
    private final List<String> withFields;
    private final Boolean withoutTotalHits;

    private SimpleQueryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.datasetName = builder.datasetName;
        this.nextToken = builder.nextToken;
        this.maxResults = builder.maxResults;
        this.query = builder.query;
        this.sort = builder.sort;
        this.order = builder.order;
        this.aggregations = builder.aggregations;
        this.withFields = builder.withFields;
        this.withoutTotalHits = builder.withoutTotalHits;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String bucket() {
        return bucket;
    }

    public String datasetName() {
        return datasetName;
    }

    public String nextToken() {
        return nextToken;
    }

    public Integer maxResults() {
        return maxResults;
    }

    public SimpleQuery query() {
        return query;
    }

    public String sort() {
        return sort;
    }

    public String order() {
        return order;
    }

    public List<Aggregation> aggregations() {
        return aggregations;
    }

    public List<String> withFields() {
        return withFields;
    }

    public Boolean withoutTotalHits() {
        return withoutTotalHits;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String datasetName;
        private String nextToken;
        private Integer maxResults;
        private SimpleQuery query;
        private String sort;
        private String order;
        private List<Aggregation> aggregations;
        private List<String> withFields;
        private Boolean withoutTotalHits;

        private Builder() {
            super();
        }

        private Builder(SimpleQueryRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.datasetName = request.datasetName;
            this.nextToken = request.nextToken;
            this.maxResults = request.maxResults;
            this.query = request.query;
            this.sort = request.sort;
            this.order = request.order;
            this.aggregations = request.aggregations;
            this.withFields = request.withFields;
            this.withoutTotalHits = request.withoutTotalHits;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.datasetName = value;
            return this;
        }

        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }

        public Builder maxResults(Integer value) {
            this.maxResults = value;
            return this;
        }

        public Builder query(SimpleQuery value) {
            this.query = value;
            return this;
        }

        public Builder sort(String value) {
            this.sort = value;
            return this;
        }

        public Builder order(String value) {
            this.order = value;
            return this;
        }

        public Builder aggregations(List<Aggregation> value) {
            this.aggregations = value;
            return this;
        }

        public Builder withFields(List<String> value) {
            this.withFields = value;
            return this;
        }

        public Builder withoutTotalHits(Boolean value) {
            this.withoutTotalHits = value;
            return this;
        }

        public SimpleQueryRequest build() {
            return new SimpleQueryRequest(this);
        }
    }
}
