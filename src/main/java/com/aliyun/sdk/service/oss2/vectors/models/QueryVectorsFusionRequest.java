package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the QueryVectorsFusion operation.
 */
public final class QueryVectorsFusionRequest extends VectorRequestModel {
    private final String bucket;

    private QueryVectorsFusionRequest(Builder builder) {
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
     * The knn vector queries. A single vector query is also represented as a one-element list.
     */
    public List<Knn> knn() {
        return (List<Knn>)this.bodyFields.get("knn");
    }

    /**
     * The conditions of the scalar query and the full text query.
     */
    public Object query() {
        return this.bodyFields.get("query");
    }

    /**
     * The multi-way hybrid retriever.
     */
    public Retriever retriever() {
        return (Retriever)this.bodyFields.get("retriever");
    }

    /**
     * Whether to return the metadata. Default value: false.
     */
    public Boolean returnMetadata() {
        return (Boolean)this.bodyFields.get("returnMetadata");
    }

    /**
     * The metadata fields to return. It takes effect only when returnMetadata is true.
     */
    public List<String> returnMetadataFields() {
        return (List<String>)this.bodyFields.get("returnMetadataFields");
    }

    /**
     * The partition keys to access. The server routes the query to the specified partitions.
     */
    public List<String> partitionKeys() {
        return (List<String>)this.bodyFields.get("partitionKeys");
    }

    /**
     * The number of the rows returned by the request. Default value: 10.
     */
    public Integer limit() {
        return (Integer)this.bodyFields.get("limit");
    }

    /**
     * The token for the next page. It is supported only when the request contains
     * the query parameter.
     */
    public String nextToken() {
        return (String)this.bodyFields.get("nextToken");
    }

    /**
     * The sort fields. A maximum of 3 sort fields are supported.
     */
    public Object sort() {
        return this.bodyFields.get("sort");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(QueryVectorsFusionRequest from) {
            super(from);
            this.bucket = from.bucket;
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
         * The knn vector queries. A single vector query must also be provided as a one-element list.
         */
        public Builder knn(List<Knn> value) {
            requireNonNull(value);
            this.bodyFields.put("knn", value);
            return this;
        }

        /**
         * The conditions of the scalar query and the full text query.
         */
        public Builder query(Object value) {
            requireNonNull(value);
            this.bodyFields.put("query", value);
            return this;
        }

        /**
         * The multi-way hybrid retriever.
         */
        public Builder retriever(Retriever value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", value);
            return this;
        }

        /**
         * Whether to return the metadata. Default value: false.
         */
        public Builder returnMetadata(Boolean value) {
            requireNonNull(value);
            this.bodyFields.put("returnMetadata", value);
            return this;
        }

        /**
         * The metadata fields to return. It takes effect only when returnMetadata is true.
         */
        public Builder returnMetadataFields(List<String> value) {
            requireNonNull(value);
            this.bodyFields.put("returnMetadataFields", value);
            return this;
        }

        /**
         * The partition keys to access. The server routes the query to the specified partitions.
         */
        public Builder partitionKeys(List<String> value) {
            requireNonNull(value);
            this.bodyFields.put("partitionKeys", value);
            return this;
        }

        /**
         * The number of the rows returned by the request. Default value: 10.
         */
        public Builder limit(Integer value) {
            requireNonNull(value);
            this.bodyFields.put("limit", value);
            return this;
        }

        /**
         * The token for the next page. It is supported only when the request contains
         * the query parameter.
         */
        public Builder nextToken(String value) {
            requireNonNull(value);
            this.bodyFields.put("nextToken", value);
            return this;
        }

        /**
         * The sort fields. A maximum of 3 sort fields are supported.
         */
        public Builder sort(Object value) {
            requireNonNull(value);
            this.bodyFields.put("sort", value);
            return this;
        }

        public QueryVectorsFusionRequest build() {
            return new QueryVectorsFusionRequest(this);
        }
    }
}
