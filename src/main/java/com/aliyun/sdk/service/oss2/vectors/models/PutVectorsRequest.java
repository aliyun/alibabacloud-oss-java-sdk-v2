package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectors operation.
 */
public final class PutVectorsRequest extends RequestModel {
    private final String bucket;
    private final VectorsConfiguration vectorsConfiguration;

    private PutVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.vectorsConfiguration = builder.vectorsConfiguration;
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
    public VectorsConfiguration vectorsConfiguration() {
        return vectorsConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private VectorsConfiguration vectorsConfiguration;

        private Builder() {
            super();
            this.vectorsConfiguration = new VectorsConfiguration();
        }

        private Builder(PutVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.vectorsConfiguration = request.vectorsConfiguration;
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
            this.vectorsConfiguration = this.vectorsConfiguration.toBuilder().indexName(value).build();
            return this;
        }

        /**
         * The vectors to insert.
         */
        public Builder vectorsMap(List<Map<String, Object>> value) {
            this.vectorsConfiguration = this.vectorsConfiguration.toBuilder().vectors(value).build();
            return this;
        }

        /**
         * The vectors to insert.
         */
        public Builder vectors(List<PutInputVector> value) {
            List<Map<String, Object>> vectorMaps = value.stream()
                    .map(vector -> {
                        Map<String, Object> vectorMap = new java.util.HashMap<>();
                        vectorMap.put("data", vector.data());
                        vectorMap.put("key", vector.key());
                        vectorMap.put("metadata", vector.metadata());
                        return vectorMap;
                    })
                    .collect(Collectors.toList());
            this.vectorsConfiguration = this.vectorsConfiguration.toBuilder().vectors(vectorMaps).build();
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder vectorsConfiguration(VectorsConfiguration vectorsConfiguration) {
            requireNonNull(vectorsConfiguration);
            this.vectorsConfiguration = vectorsConfiguration;
            return this;
        }

        public PutVectorsRequest build() {
            return new PutVectorsRequest(this);
        }
    }
}