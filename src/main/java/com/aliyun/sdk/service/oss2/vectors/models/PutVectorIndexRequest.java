package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectorIndex operation.
 */
public final class PutVectorIndexRequest extends RequestModel {
    private final String bucket;
    private final VectorIndexConfiguration vectorIndexConfiguration;

    private PutVectorIndexRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.vectorIndexConfiguration = builder.vectorIndexConfiguration;
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
     * The name of the vector index.
     */
    public String indexName() {
        return vectorIndexConfiguration.indexName();
    }

    /**
     * The configuration for the vector index.
     */
    public VectorIndexConfiguration vectorIndexConfiguration() {
        return vectorIndexConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private VectorIndexConfiguration vectorIndexConfiguration;

        private Builder() {
            super();
            this.vectorIndexConfiguration = VectorIndexConfiguration.newBuilder().build();
        }

        private Builder(PutVectorIndexRequest from) {
            super(from);
            this.bucket = from.bucket;
            this.vectorIndexConfiguration = from.vectorIndexConfiguration;
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
            this.vectorIndexConfiguration = this.vectorIndexConfiguration.toBuilder().indexName(value).build();
            return this;
        }

        /**
         * The data type of the vector.
         */
        public Builder dataType(String value) {
            requireNonNull(value);
            this.vectorIndexConfiguration = this.vectorIndexConfiguration.toBuilder().dataType(value).build();
            return this;
        }

        /**
         * The dimension of the vector.
         */
        public Builder dimension(Integer value) {
            requireNonNull(value);
            this.vectorIndexConfiguration = this.vectorIndexConfiguration.toBuilder().dimension(value).build();
            return this;
        }

        /**
         * The distance metric used for the index.
         */
        public Builder distanceMetric(String value) {
            requireNonNull(value);
            this.vectorIndexConfiguration = this.vectorIndexConfiguration.toBuilder().distanceMetric(value).build();
            return this;
        }

        /**
         * The metadata of the index.
         */
        public Builder metadata(Map<String, Object> value) {
            requireNonNull(value);
            this.vectorIndexConfiguration = this.vectorIndexConfiguration.toBuilder().metadata(value).build();
            return this;
        }

        public Builder vectorIndexConfiguration(VectorIndexConfiguration value) {
            requireNonNull(value);
            this.vectorIndexConfiguration = value;
            return this;
        }

        public PutVectorIndexRequest build() {
            return new PutVectorIndexRequest(this);
        }
    }
}