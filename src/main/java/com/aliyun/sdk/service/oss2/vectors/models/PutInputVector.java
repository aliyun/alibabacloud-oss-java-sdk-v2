package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.Map;

/**
 * The input vector for PutVectors operation.
 */
public class PutInputVector {
    private Map<String, Object> data;
    private String key;
    private Map<String, Object> metadata;

    public PutInputVector() {
    }

    private PutInputVector(Builder builder) {
        this.data = builder.data;
        this.key = builder.key;
        this.metadata = builder.metadata;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The vector data.
     */
    public Map<String, Object> data() {
        return data;
    }

    /**
     * The key of the vector.
     */
    public String key() {
        return key;
    }

    /**
     * The metadata of the vector.
     */
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
            super();
        }

        private Builder(PutInputVector from) {
            this.data = from.data;
            this.key = from.key;
            this.metadata = from.metadata;
        }

        /**
         * The vector data.
         */
        public Builder data(Map<String, Object> value) {
            this.data = value;
            return this;
        }

        /**
         * The key of the vector.
         */
        public Builder key(String value) {
            this.key = value;
            return this;
        }

        /**
         * The metadata of the vector.
         */
        public Builder metadata(Map<String, Object> value) {
            this.metadata = value;
            return this;
        }

        public PutInputVector build() {
            return new PutInputVector(this);
        }
    }
}
