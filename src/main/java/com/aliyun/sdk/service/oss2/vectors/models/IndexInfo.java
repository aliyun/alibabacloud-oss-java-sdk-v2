package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * Model for index info
 */
public class IndexInfo {
    @JsonProperty("index")
    private Map<String, Object> index;

    public IndexInfo() {
    }

    private IndexInfo(Builder builder) {
        this.index = builder.index;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The index information.
     */
    public Map<String, Object> index() {
        return index;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Map<String, Object> index;

        private Builder() {
        }

        private Builder(IndexInfo from) {
            this.index = from.index;
        }

        /**
         * The index information.
         */
        public Builder index(Map<String, Object> index) {
            requireNonNull(index);
            this.index = index;
            return this;
        }

        public IndexInfo build() {
            return new IndexInfo(this);
        }
    }
}