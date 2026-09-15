package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The knn vector query of the QueryVectorsFusion operation.
 */
public class Knn {
    @JsonProperty("field")
    private String field;
    @JsonProperty("queryVector")
    private Object queryVector;
    @JsonProperty("topK")
    private Integer topK;
    @JsonProperty("filter")
    private Object filter;
    @JsonProperty("numCandidates")
    private Integer numCandidates;
    @JsonProperty("boost")
    private Float boost;

    public Knn() {
    }

    private Knn(Builder builder) {
        this.field = builder.field;
        this.queryVector = builder.queryVector;
        this.topK = builder.topK;
        this.filter = builder.filter;
        this.numCandidates = builder.numCandidates;
        this.boost = builder.boost;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the vector field.
     */
    public String field() {
        return field;
    }

    /**
     * The query vector.
     */
    public Object queryVector() {
        return queryVector;
    }

    /**
     * The number of the top K results returned by the knn query. Default value: 10.
     */
    public Integer topK() {
        return topK;
    }

    /**
     * The pre-filter of the knn query. The syntax is the same as the query parameter.
     */
    public Object filter() {
        return filter;
    }

    /**
     * The number of the candidates. It enlarges the search scope to improve the recall rate.
     * The value must be greater than or equal to topK.
     */
    public Integer numCandidates() {
        return numCandidates;
    }

    /**
     * The weight of the query. It is a non-negative float32 number. Default value: 1.0.
     */
    public Float boost() {
        return boost;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String field;
        private Object queryVector;
        private Integer topK;
        private Object filter;
        private Integer numCandidates;
        private Float boost;

        private Builder() {
        }

        private Builder(Knn from) {
            this.field = from.field;
            this.queryVector = from.queryVector;
            this.topK = from.topK;
            this.filter = from.filter;
            this.numCandidates = from.numCandidates;
            this.boost = from.boost;
        }

        /**
         * The name of the vector field.
         */
        public Builder field(String field) {
            this.field = field;
            return this;
        }

        /**
         * The query vector.
         */
        public Builder queryVector(Object queryVector) {
            this.queryVector = queryVector;
            return this;
        }

        /**
         * The number of the top K results returned by the knn query. Default value: 10.
         */
        public Builder topK(Integer topK) {
            this.topK = topK;
            return this;
        }

        /**
         * The pre-filter of the knn query. The syntax is the same as the query parameter.
         */
        public Builder filter(Object filter) {
            this.filter = filter;
            return this;
        }

        /**
         * The number of the candidates. It enlarges the search scope to improve the recall rate.
         * The value must be greater than or equal to topK.
         */
        public Builder numCandidates(Integer numCandidates) {
            this.numCandidates = numCandidates;
            return this;
        }

        /**
         * The weight of the query. It is a non-negative float32 number. Default value: 1.0.
         */
        public Builder boost(Float boost) {
            this.boost = boost;
            return this;
        }

        public Knn build() {
            return new Knn(this);
        }
    }
}
