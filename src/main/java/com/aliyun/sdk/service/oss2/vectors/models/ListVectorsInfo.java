package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Model for list vectors info with nextToken and vectors list
 */
public class ListVectorsInfo {
    @JsonProperty("nextToken")
    private String nextToken;

    @JsonProperty("vectors")
    private List<ListOutputVector> vectors;

    public ListVectorsInfo() {
    }

    private ListVectorsInfo(Builder builder) {
        this.nextToken = builder.nextToken;
        this.vectors = builder.vectors;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The token for the next page of vectors.
     */
    public String nextToken() {
        return nextToken;
    }

    /**
     * The list of vectors retrieved.
     */
    public List<ListOutputVector> vectors() {
        return vectors;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String nextToken;
        private List<ListOutputVector> vectors;

        private Builder() {
        }

        private Builder(ListVectorsInfo from) {
            this.nextToken = from.nextToken;
            this.vectors = from.vectors;
        }

        /**
         * The token for the next page of vectors.
         */
        public Builder nextToken(String nextToken) {
            this.nextToken = nextToken;
            return this;
        }

        /**
         * The list of vectors retrieved.
         */
        public Builder vectors(List<ListOutputVector> vectors) {
            this.vectors = vectors;
            return this;
        }

        public ListVectorsInfo build() {
            return new ListVectorsInfo(this);
        }
    }
}