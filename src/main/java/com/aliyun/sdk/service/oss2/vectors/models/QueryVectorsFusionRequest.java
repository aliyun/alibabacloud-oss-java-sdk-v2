package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
     * The knn vector queries, exactly as they are sent to the service. A single vector query is
     * also represented as a one-element list.
     * <p>
     * The elements are the raw JSON objects, so attributes that have no strongly-typed model yet
     * are preserved.
     */
    public List<?> knn() {
        Object value = this.bodyFields.get("knn");
        return value instanceof List ? (List<?>) value : null;
    }

    /**
     * The conditions of the scalar query and the full text query.
     */
    public Object query() {
        return this.bodyFields.get("query");
    }

    /**
     * The multi-way hybrid retriever, exactly as it is sent to the service. It carries the raw
     * JSON object, so attributes that have no strongly-typed model yet are preserved.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> retriever() {
        Object value = this.bodyFields.get("retriever");
        return value instanceof Map ? (Map<String, Object>) value : null;
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
         * <p>
         * The elements are the raw JSON objects: pass {@link Knn} instances directly, or
         * {@code Map<String, Object>} values when the typed model does not cover every attribute.
         * {@link Knn} instances are stored as their raw representation via {@link Knn#toMap()}.
         */
        public Builder knn(List<?> value) {
            requireNonNull(value);
            this.bodyFields.put("knn", toRawKnnList(value));
            return this;
        }

        /**
         * The single knn vector query. It is a convenience overload that wraps the given knn
         * into a one-element list internally.
         */
        public Builder knn(Knn value) {
            requireNonNull(value);
            this.bodyFields.put("knn", Arrays.asList(value.toMap()));
            return this;
        }

        private static List<?> toRawKnnList(List<?> value) {
            List<Object> raw = new ArrayList<>(value.size());
            for (Object element : value) {
                raw.add(element instanceof Knn ? ((Knn) element).toMap() : element);
            }
            return raw;
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
         * The multi-way hybrid retriever as the raw JSON object. Use it when the retriever has
         * attributes that no strongly-typed model covers yet: the map is passed through verbatim.
         */
        public Builder retriever(Map<String, Object> value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", value);
            return this;
        }

        /**
         * The rrf compound retriever, stored as {@code {"rrf": ...}}.
         */
        public Builder retriever(RrfRetriever value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", wrap("rrf", value.toMap()));
            return this;
        }

        /**
         * The weight compound retriever, stored as {@code {"weight": ...}}.
         */
        public Builder retriever(WeightRetriever value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", wrap("weight", value.toMap()));
            return this;
        }

        /**
         * The simple leaf retriever, stored as {@code {"simple": ...}}.
         */
        public Builder retriever(SimpleRetriever value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", wrap("simple", value.toMap()));
            return this;
        }

        private static Map<String, Object> wrap(String key, Map<String, Object> body) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(key, body);
            return map;
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
