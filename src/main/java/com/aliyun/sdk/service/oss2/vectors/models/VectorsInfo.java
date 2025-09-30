package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * Model for vectors info
 */
public class VectorsInfo {

    @JsonProperty("vectors")
    private List<Map<String, Object>> vectors;

    public VectorsInfo() {
    }

    private VectorsInfo(Builder builder) {
        this.vectors = builder.vectors;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * The list of vectors retrieved.
     */
    public List<Map<String, Object>> vectors() {
        return vectors;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String nextToken;
        private List<Map<String, Object>> vectors;

        private Builder() {
        }

        private Builder(VectorsInfo from) {
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
        public Builder vectors(List<Map<String, Object>> vectors) {
            requireNonNull(vectors);
            this.vectors = vectors;
            return this;
        }

        public VectorsInfo build() {
            return new VectorsInfo(this);
        }
    }
}