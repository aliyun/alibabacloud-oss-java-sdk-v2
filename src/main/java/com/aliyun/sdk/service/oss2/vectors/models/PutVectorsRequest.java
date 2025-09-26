package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.PutVectorsRequestJson;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectors operation.
 */
public final class PutVectorsRequest extends RequestModel {
    private final String bucket;
    private final PutVectorsRequestJson putVectorsRequestJson;

    private PutVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.putVectorsRequestJson = builder.putVectorsRequestJson;
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
    public PutVectorsRequestJson putVectorsRequestJson() {
        return putVectorsRequestJson;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private PutVectorsRequestJson putVectorsRequestJson;

        private Builder() {
            super();
            this.putVectorsRequestJson = new PutVectorsRequestJson();
        }

        private Builder(PutVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.putVectorsRequestJson = request.putVectorsRequestJson;
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
            this.putVectorsRequestJson.indexName = value;
            return this;
        }

        /**
         * The vectors to insert (Map<String, Object> type).
         */
        public Builder vectorsMap(List<Map<String, Object>> value) {
            this.putVectorsRequestJson.vectors = value;
            return this;
        }

        /**
         * The vectors to insert (PutInputVector type).
         */
        public Builder vectors(List<PutInputVector> value) {
            this.putVectorsRequestJson.vectors = value.stream()
                    .map(vector -> {
                        Map<String, Object> vectorMap = new java.util.HashMap<>();
                        vectorMap.put("data", vector.data());
                        vectorMap.put("key", vector.key());
                        vectorMap.put("metadata", vector.metadata());
                        return vectorMap;
                    })
                    .collect(Collectors.toList());
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder putVectorsRequestJson(PutVectorsRequestJson putVectorsRequestJson) {
            requireNonNull(putVectorsRequestJson);
            this.putVectorsRequestJson = putVectorsRequestJson;
            return this;
        }

        public PutVectorsRequest build() {
            return new PutVectorsRequest(this);
        }
    }
}
