package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the QueryVectors operation.
 */
public final class QueryVectorsRequest extends RequestModel {
    private final String bucket;
    private final QueryVectorsConfiguration queryVectorsConfiguration;

    private QueryVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.queryVectorsConfiguration = builder.queryVectorsConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The request body schema.
     */
    public QueryVectorsConfiguration queryVectorsConfiguration() {
        return queryVectorsConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private QueryVectorsConfiguration queryVectorsConfiguration;

        private Builder() {
            super();
            this.queryVectorsConfiguration = new QueryVectorsConfiguration();
        }

        private Builder(QueryVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.queryVectorsConfiguration = request.queryVectorsConfiguration;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The filter for querying vectors.
         */
        public Builder filter(Map<String, Object> value) {
            this.queryVectorsConfiguration = this.queryVectorsConfiguration.toBuilder().filter(value).build();
            return this;
        }

        /**
         * The name of the index.
         */
        public Builder indexName(String value) {
            this.queryVectorsConfiguration = this.queryVectorsConfiguration.toBuilder().indexName(value).build();
            return this;
        }

        /**
         * The query vector.
         */
        public Builder queryVector(QueryVectorsConfiguration.QueryVector value) {
            this.queryVectorsConfiguration = this.queryVectorsConfiguration.toBuilder().queryVector(value).build();
            return this;
        }

        /**
         * Whether to return distance.
         */
        public Builder returnDistance(Boolean value) {
            this.queryVectorsConfiguration = this.queryVectorsConfiguration.toBuilder().returnDistance(value).build();
            return this;
        }

        /**
         * Whether to return metadata.
         */
        public Builder returnMetadata(Boolean value) {
            this.queryVectorsConfiguration = this.queryVectorsConfiguration.toBuilder().returnMetadata(value).build();
            return this;
        }

        /**
         * The number of top K vectors to return.
         */
        public Builder topK(Integer value) {
            this.queryVectorsConfiguration = this.queryVectorsConfiguration.toBuilder().topK(value).build();
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder queryVectorsConfiguration(QueryVectorsConfiguration queryVectorsConfiguration) {
            requireNonNull(queryVectorsConfiguration);
            this.queryVectorsConfiguration = queryVectorsConfiguration;
            return this;
        }

        public QueryVectorsRequest build() {
            return new QueryVectorsRequest(this);
        }
    }
}