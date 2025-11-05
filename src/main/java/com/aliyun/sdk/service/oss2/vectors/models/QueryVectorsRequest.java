package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the QueryVectors operation.
 */
public final class QueryVectorsRequest extends VectorRequestModel {
    private final String bucket;

    private QueryVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
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
     * The name of the index.
     */
    public String indexName() {
        return (String)this.bodyFields.get("indexName");
    }

    /**
     * The query vector.
     */
    public Object queryVector() {
        return this.bodyFields.get("queryVector");
    }

    /**
     * The number of top K vectors to return.
     */
    public Integer topK() {
        return (Integer)this.bodyFields.get("topK");
    }

    /**
     * The filter for querying vectors.
     */
    public Object filter() {
        return this.bodyFields.get("filter");
    }

    /**
     * Whether to return distance.
     */
    public Boolean returnDistance() {
        return (Boolean)this.bodyFields.get("returnDistance");
    }

    /**
     * Whether to return metadata.
     */
    public Boolean returnMetadata() {
        return (Boolean)this.bodyFields.get("returnMetadata");
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(QueryVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
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
         * The name of the index.
         */
        public Builder indexName(String value) {
            requireNonNull(value);
            this.bodyFields.put("indexName", value);
            return this;
        }

        /**
         * The query vector.
         */
        public Builder queryVector(Object value) {
            requireNonNull(value);
            this.bodyFields.put("queryVector", value);
            return this;
        }

        /**
         * The number of top K vectors to return.
         */
        public Builder topK(Integer value) {
            requireNonNull(value);
            this.bodyFields.put("topK", value);
            return this;
        }

        /**
         * The filter for querying vectors.
         */
        public Builder filter(Object value) {
            requireNonNull(value);
            this.bodyFields.put("filter", value);
            return this;
        }

        /**
         * Whether to return distance.
         */
        public Builder returnDistance(Boolean value) {
            requireNonNull(value);
            this.bodyFields.put("returnDistance", value);
            return this;
        }

        /**
         * Whether to return metadata.
         */
        public Builder returnMetadata(Boolean value) {
            requireNonNull(value);
            this.bodyFields.put("returnMetadata", value);
            return this;
        }

        public QueryVectorsRequest build() {
            return new QueryVectorsRequest(this);
        }
    }
}