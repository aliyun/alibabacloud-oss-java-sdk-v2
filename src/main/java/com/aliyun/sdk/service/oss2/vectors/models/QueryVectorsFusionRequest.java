package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Objects.requireNonNull;

/**
 * The request for the QueryVectorsFusion operation.
 */
public final class QueryVectorsFusionRequest extends VectorRequestModel {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

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
     * It returns {@code null} when the knn was set from a raw JSON string via
     * {@link Builder#knn(String)}.
     */
    @SuppressWarnings("unchecked")
    public List<Knn> knn() {
        Object value = this.bodyFields.get("knn");
        return value instanceof List ? (List<Knn>) value : null;
    }

    /**
     * The conditions of the scalar query and the full text query.
     */
    public Object query() {
        return this.bodyFields.get("query");
    }

    /**
     * The multi-way hybrid retriever. It returns {@code null} when the retriever was set from a
     * raw JSON string via {@link Builder#retriever(String)}.
     */
    public Retriever retriever() {
        Object value = this.bodyFields.get("retriever");
        return value instanceof Retriever ? (Retriever) value : null;
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

    /**
     * Parses a raw JSON string into a Jackson tree so that it is serialized as a nested JSON
     * structure (instead of an escaped string literal), preserving any current or future fields
     * verbatim.
     */
    private static JsonNode parseJson(String json) {
        try {
            return JSON_MAPPER.readTree(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse the JSON string", e);
        }
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
         * The single knn vector query. It is a convenience overload that wraps the given knn
         * into a one-element list internally.
         */
        public Builder knn(Knn value) {
            requireNonNull(value);
            this.bodyFields.put("knn", Arrays.asList(value));
            return this;
        }

        /**
         * Sets the knn vector queries from a raw JSON string. This is a flexible overload for the
         * nested knn structure: the JSON is passed through as-is, so any current or future knn
         * fields are supported without a matching strongly-typed model. The value may be a single
         * knn object or an array of knn objects. Use {@link #knn(List)} or {@link #knn(Knn)} when
         * you prefer the compile-time-safe, strongly-typed builder.
         *
         * @param value the knn JSON string, for example
         *              {@code [{"field":"vector","queryVector":[0.1,0.2],"topK":10}]}
         */
        public Builder knn(String value) {
            requireNonNull(value);
            this.bodyFields.put("knn", parseJson(value));
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
         * Sets the multi-way hybrid retriever from a raw JSON string. This is a flexible overload
         * for the deeply nested retriever structure (rrf/weight and their nested knn/simple
         * components): the JSON is passed through as-is, so any current or future fields are
         * supported without a matching strongly-typed model. Use {@link #retriever(Retriever)}
         * when you prefer the compile-time-safe, strongly-typed builder.
         *
         * @param value the retriever JSON string, for example
         *              {@code {"rrf":{"k":50,"windowSize":100,"retrievers":[ ... ]}}}
         */
        public Builder retriever(String value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", parseJson(value));
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
