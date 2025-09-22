package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorIndexConfigurationJson;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectorIndex operation.
 */
public final class PutVectorIndexRequest extends RequestModel {
    private final String bucket;
    private final VectorIndexConfigurationJson vectorIndexConfigurationJson;

    private PutVectorIndexRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.vectorIndexConfigurationJson = builder.vectorIndexConfigurationJson;
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
     * The request body schema.
     */
    public VectorIndexConfigurationJson vectorIndexConfigurationJson() {
        return vectorIndexConfigurationJson;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private VectorIndexConfigurationJson vectorIndexConfigurationJson;

        private Builder() {
            super();
            this.vectorIndexConfigurationJson = new VectorIndexConfigurationJson();
        }

        private Builder(PutVectorIndexRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.vectorIndexConfigurationJson = request.vectorIndexConfigurationJson;
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
         * The data type of the vector.
         */
        public Builder dataType(String value) {
            this.vectorIndexConfigurationJson.dataType = value;
            return this;
        }

        /**
         * The dimension of the vector.
         */
        public Builder dimension(Integer value) {
            this.vectorIndexConfigurationJson.dimension = value;
            return this;
        }

        /**
         * The distance metric of the vector.
         */
        public Builder distanceMetric(String value) {
            this.vectorIndexConfigurationJson.distanceMetric = value;
            return this;
        }

        /**
         * The name of the index.
         */
        public Builder indexName(String value) {
            this.vectorIndexConfigurationJson.indexName = value;
            return this;
        }

        /**
         * The metadata of the vector index.
         */
        public Builder metadata(java.util.Map<String, Object> value) {
            this.vectorIndexConfigurationJson.metadata = value;
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder vectorIndexConfigurationJson(VectorIndexConfigurationJson value) {
            requireNonNull(value);
            this.vectorIndexConfigurationJson = value;
            return this;
        }

        public PutVectorIndexRequest build() {
            return new PutVectorIndexRequest(this);
        }
    }
}
