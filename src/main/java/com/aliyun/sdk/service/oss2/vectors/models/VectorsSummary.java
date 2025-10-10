package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * The summary of vectors.
 */
public class VectorsSummary {
    @JsonProperty("data")
    private Map<String, Object> data;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    public VectorsSummary() {
    }

    private VectorsSummary(Builder builder) {
        this.data = builder.data;
        this.key = builder.key;
        this.metadata = builder.metadata;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Map<String, Object> data() {
        return data;
    }

    public String key() {
        return key;
    }

    public Map<String, Object> metadata() {
        return metadata;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Map<String, Object> data;
        private String key;
        private Map<String, Object> metadata;

        private Builder() {
        }

        private Builder(VectorsSummary from) {
            this.data = from.data;
            this.key = from.key;
            this.metadata = from.metadata;
        }

        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public VectorsSummary build() {
            return new VectorsSummary(this);
        }
    }
}