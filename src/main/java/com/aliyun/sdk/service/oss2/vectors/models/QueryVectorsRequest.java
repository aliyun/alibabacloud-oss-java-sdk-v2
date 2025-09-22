package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the QueryVectors operation.
 */
public final class QueryVectorsRequest extends RequestModel {
    private final String bucket;
    private final Map<String, Object> filter;
    private final String indexName;
    private final Map<String, Object> queryVector;
    private final Boolean returnDistance;
    private final Boolean returnMetadata;
    private final Integer topK;

    private QueryVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.filter = builder.filter;
        this.indexName = builder.indexName;
        this.queryVector = builder.queryVector;
        this.returnDistance = builder.returnDistance;
        this.returnMetadata = builder.returnMetadata;
        this.topK = builder.topK;
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
     * The filter conditions for querying vectors.
     */
    public Map<String, Object> filter() {
        return filter;
    }

    /**
     * The name of the index.
     */
    public String indexName() {
        return indexName;
    }

    /**
     * The query vector data.
     */
    public Map<String, Object> queryVector() {
        return queryVector;
    }

    /**
     * Whether to return distance values.
     */
    public Boolean returnDistance() {
        return returnDistance;
    }

    /**
     * Whether to return vector metadata.
     */
    public Boolean returnMetadata() {
        return returnMetadata;
    }

    /**
     * The number of nearest neighbors to return.
     */
    public Integer topK() {
        return topK;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private Map<String, Object> filter;
        private String indexName;
        private Map<String, Object> queryVector;
        private Boolean returnDistance;
        private Boolean returnMetadata;
        private Integer topK;

        private Builder() {
            super();
        }

        private Builder(QueryVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.filter = request.filter;
            this.indexName = request.indexName;
            this.queryVector = request.queryVector;
            this.returnDistance = request.returnDistance;
            this.returnMetadata = request.returnMetadata;
            this.topK = request.topK;
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
         * The filter conditions for querying vectors.
         */
        public Builder filter(Map<String, Object> value) {
            this.filter = value;
            return this;
        }

        /**
         * The name of the index.
         */
        public Builder indexName(String value) {
            this.indexName = value;
            return this;
        }

        /**
         * The query vector data.
         */
        public Builder queryVector(Map<String, Object> value) {
            this.queryVector = value;
            return this;
        }

        /**
         * Whether to return distance values.
         */
        public Builder returnDistance(Boolean value) {
            this.returnDistance = value;
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean value) {
            this.returnMetadata = value;
            return this;
        }

        /**
         * The number of nearest neighbors to return.
         */
        public Builder topK(Integer value) {
            this.topK = value;
            return this;
        }

        public QueryVectorsRequest build() {
            return new QueryVectorsRequest(this);
        }
    }
}
