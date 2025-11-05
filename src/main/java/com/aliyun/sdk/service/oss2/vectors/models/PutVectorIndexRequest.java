package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectorIndex operation.
 */
public final class PutVectorIndexRequest extends VectorRequestModel {
    private final String bucket;

    private PutVectorIndexRequest(Builder builder) {
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
     * The data type of the vector.
     */
    public String dataType() {
        return (String)this.bodyFields.get("dataType");
    }

    /**
     * The dimension of the vector.
     */
    public Integer dimension() {
        return (Integer)this.bodyFields.get("dimension");
    }

    /**
     * The distance metric used for the index.
     */
    public String distanceMetric() {
        return (String)this.bodyFields.get("distanceMetric");
    }

    /**
     * The name of the index.
     */
    public String indexName() {
        return (String)this.bodyFields.get("indexName");
    }

    /**
     * The metadata of the index.
     */
    public Map<String, Object> metadata() {
        return (Map<String, Object>)this.bodyFields.get("metadata");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(PutVectorIndexRequest from) {
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
         * The name of the vector index.
         */
        public Builder indexName(String value) {
            requireNonNull(value);
            this.bodyFields.put("indexName", value);
            return this;
        }

        /**
         * The data type of the vector.
         */
        public Builder dataType(String value) {
            requireNonNull(value);
            this.bodyFields.put("dataType", value);
            return this;
        }

        /**
         * The dimension of the vector.
         */
        public Builder dimension(Integer value) {
            requireNonNull(value);
            this.bodyFields.put("dimension", value);
            return this;
        }

        /**
         * The distance metric used for the index.
         */
        public Builder distanceMetric(String value) {
            requireNonNull(value);
            this.bodyFields.put("distanceMetric", value);
            return this;
        }

        /**
         * The metadata of the index.
         */
        public Builder metadata(Map<String, Object> value) {
            requireNonNull(value);
            this.bodyFields.put("metadata", value);
            return this;
        }

        public PutVectorIndexRequest build() {
            return new PutVectorIndexRequest(this);
        }
    }
}